import 'dart:convert';
import 'dart:io';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:path_provider/path_provider.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:flutter_sound/flutter_sound.dart';

class Tile2Widget extends StatefulWidget {
  const Tile2Widget({super.key});

  @override
  _Tile2WidgetState createState() => _Tile2WidgetState();
}

class _Tile2WidgetState extends State<Tile2Widget>
    with SingleTickerProviderStateMixin {
  bool isRecording = false;
  String? accessToken;
  String transcribedText = "";
  String aiResponse = "";
  String conversationId = "";
  final FlutterSoundRecorder _recorder = FlutterSoundRecorder();
  String? recordedFilePath;

  @override
  void initState() {
    super.initState();
    _fetchAuthToken(); // Fetch API token when the page renders
    _initializeRecorder();
  }

  Future<void> _initializeRecorder() async {
    final status = await Permission.microphone.request();
    if (status != PermissionStatus.granted) {
      throw RecordingPermissionException('Microphone permission not granted');
    }
    await _recorder.openRecorder();
  }

  Future<void> _fetchAuthToken() async {
    const String authUrl = "http://188.121.113.116:8000/token";
    try {
      final response = await http.post(
        Uri.parse(authUrl),
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: {
          "username": "hinava",
          "password": "AO5!JGlEsOl1;MP",
        },
      );

      if (response.statusCode == 200) {
        final responseData = json.decode(response.body);
        setState(() {
          accessToken = responseData["access_token"];
        });
        print("Access token fetched: $accessToken");
      } else {
        print("Failed to fetch access token: ${response.statusCode}");
      }
    } catch (e) {
      print("Error fetching token: $e");
    }
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

  Future<void> _stopRecording() async {
    if (isRecording) {
      await _recorder.stopRecorder();
    }
    setState(() {
      isRecording = false;
    });

    if (recordedFilePath != null) {
      await _sendVoiceToTranscriptionService(recordedFilePath!);
    }
  }

  Future<void> _sendVoiceToTranscriptionService(String filePath) async {
    if (accessToken == null) {
      print("No access token available.");
      return;
    }

    const String apiUrl = "http://188.121.113.116:8000/speech_recognition/";
    try {
      var request = http.MultipartRequest("POST", Uri.parse(apiUrl))
        ..headers.addAll({
          "Authorization": "Bearer $accessToken",
          "Accept": "application/json",
        })
        ..files.add(await http.MultipartFile.fromPath("file", filePath));

      var response = await request.send();
      if (response.statusCode == 200) {
        final responseData = await response.stream.bytesToString();
        final jsonResponse = json.decode(responseData);
        setState(() {
          transcribedText = jsonResponse["results"];
        });
        print("Transcribed text: $transcribedText");

        // Call chatbot API after receiving the transcribed text
        await _sendTextToChatBot(transcribedText);
      } else {
        print("Failed to transcribe audio: ${response.statusCode}");
      }
    } catch (e) {
      print("Error sending voice file: $e");
    }
  }

  Future<void> _sendTextToChatBot(String text) async {
    const String chatbotUrl = "http://79.127.12.135:5600/chatbot_simple";

    try {
      final response = await http.post(
        Uri.parse(chatbotUrl),
        headers: {"Content-Type": "application/json"},
        body: jsonEncode({
          "query": text,
          "conversation_id": conversationId,
        }),
      );

      if (response.statusCode == 200) {
        // Decode response with UTF-8 support
        final responseData = json.decode(utf8.decode(response.bodyBytes));
        setState(() {
          aiResponse = responseData["answer"];
          conversationId =
              responseData["conversation_id"]; // Store for future queries
        });
        print("AI Response: $aiResponse");
      } else {
        print("Failed to get AI response: ${response.statusCode}");
      }
    } catch (e) {
      print("Error calling chatbot API: $e");
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
      backgroundColor: const Color(0xFF0A0E1A), // Dark background
      body: Stack(
        alignment: Alignment.center,
        children: [
          Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              GestureDetector(
                onTap: () async {
                  if (isRecording) {
                    await _stopRecording();
                  } else {
                    await _startRecording();
                  }
                },
                child: Stack(
                  alignment: Alignment.center,
                  children: [
                    AnimatedOpacity(
                      duration: const Duration(milliseconds: 500),
                      opacity: isRecording ? 1.0 : 0.0,
                      child: Container(
                        width: 150,
                        height: 150,
                        decoration: BoxDecoration(
                          shape: BoxShape.circle,
                          color: Colors.red.withOpacity(0.3),
                        ),
                      ),
                    ),

                    // Main recording button
                    Center(
                      child: Container(
                        width: 100,
                        height: 100,
                        decoration: BoxDecoration(
                          shape: BoxShape.circle,
                          color: isRecording ? Colors.red : Colors.blueGrey,
                          boxShadow: isRecording
                              ? [
                                  BoxShadow(
                                    color: Colors.red.withOpacity(0.5),
                                    blurRadius: 15,
                                    spreadRadius: 5,
                                  )
                                ]
                              : [],
                        ),
                        child: Icon(
                          isRecording ? Icons.stop : Icons.mic,
                          color: Colors.white,
                          size: 40,
                        ),
                      ),
                    ),
                  ],
                ),
              ),

              const SizedBox(height: 30),

              // Transcribed text bubble
              if (transcribedText.isNotEmpty)
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 20),
                  child: Align(
                    alignment: Alignment.center,
                    child: Container(
                      padding: const EdgeInsets.all(12),
                      decoration: BoxDecoration(
                        color: Colors.blueGrey,
                        borderRadius: BorderRadius.circular(12),
                      ),
                      child: Text(
                        transcribedText,
                        style:
                            const TextStyle(color: Colors.white, fontSize: 16),
                        textAlign: TextAlign.center,
                      ),
                    ),
                  ),
                ),

              const SizedBox(height: 15),

              // AI Response bubble
              if (aiResponse.isNotEmpty)
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 20),
                  child: Align(
                    alignment: Alignment.center,
                    child: Container(
                      padding: const EdgeInsets.all(12),
                      decoration: BoxDecoration(
                        color: Colors.green,
                        borderRadius: BorderRadius.circular(12),
                      ),
                      child: Text(
                        aiResponse,
                        style:
                            const TextStyle(color: Colors.white, fontSize: 16),
                        textAlign: TextAlign.center,
                      ),
                    ),
                  ),
                ),
            ],
          ),
        ],
      ),
    );
  }
}
