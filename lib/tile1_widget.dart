import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:http/http.dart' as http;
import 'package:http_parser/http_parser.dart';
import 'dart:convert';
import 'dart:io';
import 'package:permission_handler/permission_handler.dart';
import 'package:smart_robot/client.dart';
import 'package:smart_robot/main.dart';
import 'package:smart_robot/message.dart';
import 'package:wifi_iot/wifi_iot.dart';
import 'package:flutter_sound/flutter_sound.dart';
import 'package:path_provider/path_provider.dart';

class Tile1Widget extends StatefulWidget {
  const Tile1Widget({Key? key}) : super(key: key);

  @override
  State<Tile1Widget> createState() => _Tile1WidgetState();
}

class _Tile1WidgetState extends State<Tile1Widget> {
  bool isRecording = false;
  bool isLoading = false;
  final FlutterSoundRecorder _recorder = FlutterSoundRecorder();
  String? recordedFilePath;

  List<Message> messages = [];

  @override
  void initState() {
    super.initState();
    _initializeRecorder();
  }

  Future<void> _initializeRecorder() async {
    final status = await Permission.microphone.request();
    if (status != PermissionStatus.granted) {
      throw RecordingPermissionException('Microphone permission not granted');
    }
    await _recorder.openRecorder();
  }

  Future<void> _startRecording() async {
    setState(() {
      isRecording = true;
    });

    final tempDir = await getTemporaryDirectory();
    final filePath = '${tempDir.path}/voice_recording.wav';

    await _recorder.startRecorder(
      toFile: filePath,
      codec: Codec.pcm16WAV,
    );

    recordedFilePath = filePath;
  }

  Future<void> moveRobot(String place) async {
    const url = 'ws://192.168.20.22:9090';
    var markerName = place;

    await RobotChannel.checkAndMove(url, markerName);
  }

  Future<void> _stopRecording() async {
    if (isRecording) {
      await _recorder.stopRecorder();
    }
    setState(() {
      isRecording = false;
    });
  }

  Future<void> handleAIPAAandRobot() async {
    setState(() {
      isLoading = true;
    });

    try {
      // Step 1: Upload the recorded file to AIPAA API
      if (recordedFilePath == null) {
        print('No recorded file to send');
        return;
      }

      final apiResponse = await _callAIPAAApi(recordedFilePath!);
      print(apiResponse);

      // Add user's input to the messages list
      setState(() {
        messages.add(Message(text: "User: [Voice Recording]", isUser: true));
      });

      await Future.delayed(const Duration(seconds: 10));

      if (apiResponse != null) {
        // Add API's response to the messages list
        setState(() {
          messages.add(Message(text: "Bot: $apiResponse", isUser: false));
        });

        if (apiResponse.contains('علی')) {
          moveRobot('Ali Seat');
        } else if (apiResponse.contains('شارژر')) {
          moveRobot('Charger');
        } else {
          print('No action required');
        }
      } else {
        setState(() {
          messages
              .add(Message(text: "Bot: No response from API", isUser: false));
        });
      }
    } catch (e) {
      print('Error: $e');
      setState(() {
        messages.add(Message(text: "Bot: Error occurred", isUser: false));
      });
    } finally {
      setState(() {
        isLoading = false;
      });
    }
  }

  Future<String?> _callAIPAAApi(String filePath) async {
    const String url = "http://188.121.113.116:8000/speech_recognition/";

    try {
      final client = await getOAuth2Client();
      final request = http.MultipartRequest('POST', Uri.parse(url))
        ..headers.addAll({
          'accept': 'application/json',
          'authorization': 'Bearer ${client.credentials.accessToken}',
          'Content-Type': 'multipart/form-data',
        })
        ..files.add(await http.MultipartFile.fromPath(
          'file',
          filePath,
          contentType: MediaType('audio', 'wav'),
        ));

      final response = await request.send();
      if (response.statusCode == 200) {
        final responseData = await response.stream.bytesToString();
        final jsonResponse = json.decode(responseData);
        return jsonResponse['results']; // Assuming 'text' contains the response
      } else {
        print('API call failed with status: ${response.statusCode}');
        return null;
      }
    } catch (e) {
      print('Error calling AIPAA API: $e');
      return null;
    }
  }

  @override
  void dispose() {
    _recorder.closeRecorder();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFF0A0E1A),
      appBar: AppBar(
        title: const Text(
          'بیا با هم صحبت کنیم',
          style: TextStyle(color: Colors.white),
        ),
        backgroundColor: Colors.black,
      ),
      body: Stack(
        children: [
          ListView.builder(
            padding: const EdgeInsets.only(
                bottom: 100), // Add padding for the buttons
            itemCount: messages.length,
            itemBuilder: (context, index) {
              final message = messages[index];
              return Container(
                margin: const EdgeInsets.symmetric(vertical: 4, horizontal: 8),
                alignment: message.isUser
                    ? Alignment.centerRight
                    : Alignment.centerLeft,
                child: Container(
                  padding: const EdgeInsets.all(12),
                  decoration: BoxDecoration(
                    color: message.isUser ? Colors.blue : Colors.green,
                    borderRadius: BorderRadius.circular(12),
                  ),
                  child: Text(
                    message.text,
                    style: const TextStyle(color: Colors.white),
                  ),
                ),
              );
            },
          ),
          if (isLoading)
            const Center(
              child: CircularProgressIndicator(),
            ),
          Positioned(
            bottom: 24,
            right: 16,
            child: Row(
              children: [
                GestureDetector(
                  onTap: () async {
                    if (isRecording) {
                      await _stopRecording();
                    } else {
                      await _startRecording();
                    }
                  },
                  child: CircleAvatar(
                    radius: 40,
                    backgroundColor: isRecording ? Colors.red : Colors.blueGrey,
                    child: Icon(
                      isRecording ? Icons.stop : Icons.mic,
                      color: Colors.white,
                    ),
                  ),
                ),
                const SizedBox(width: 16),
                GestureDetector(
                  onTap: () async {
                    await handleAIPAAandRobot();
                  },
                  child: CircleAvatar(
                    radius: 40,
                    backgroundColor: Colors.green,
                    child: const Icon(Icons.send, color: Colors.white),
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

class RobotChannel {
  static const platform = MethodChannel('com.example.robot');

  static Future<void> checkAndMove(String url, String markerName) async {
    try {
      final result = await platform.invokeMethod('checkAndMove', {
        'url': url,
        'markerName': markerName,
      });
      print(result); // Success message
    } catch (e) {
      print("Error: $e"); // Error message
    }
  }
}
