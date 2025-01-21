import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:http_parser/http_parser.dart';
import 'dart:convert';
import 'dart:io';
import 'dart:async'; // Import for Stopwatch
import 'package:permission_handler/permission_handler.dart';
import 'package:flutter_sound/flutter_sound.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';

class Tile1Widget extends StatefulWidget {
  const Tile1Widget({super.key});

  @override
  State<Tile1Widget> createState() => _Tile1WidgetState();
}

class _Tile1WidgetState extends State<Tile1Widget> {
  bool isRecording = false;
  bool isLoading = false;
  bool isChatOpen = false; // Controls visibility of chat box
  final List<Map<String, String>> messages = [];
  final FlutterSoundRecorder _recorder = FlutterSoundRecorder();
  final FlutterSoundPlayer _player = FlutterSoundPlayer();

  // Variables to store API response times
  Duration whisperTime = Duration.zero;
  Duration chatGptTime = Duration.zero;
  Duration ttsTime = Duration.zero;

  String apiKey = dotenv.env['GPT_API_KEY'] ?? 'No API URL found'; 

  @override
  void initState() {
    super.initState();
    _initializeRecorder();
    _player.openPlayer();
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

    await _recorder.startRecorder(
      toFile: 'voice_recording.wav',
      codec: Codec.pcm16WAV,
    );
  }

  Future<void> _stopRecordingAndProcess() async {
    final filePath = await _recorder.stopRecorder();
    setState(() {
      isRecording = false;
      isLoading = true;
      isChatOpen = true; // Open chat box after user interaction
    });

    if (filePath != null) {
      final stopwatch = Stopwatch()..start();
      final transcribedText = await _transcribeVoice(File(filePath));
      stopwatch.stop();
      setState(() {
        whisperTime = stopwatch.elapsed;
      });

      if (transcribedText != null && transcribedText.isNotEmpty) {
        setState(() {
          messages.add({'role': 'user', 'content': transcribedText});
        });

        stopwatch..reset()..start();
        final responseText = await _sendTextToChatAPI(transcribedText);
        stopwatch.stop();
        setState(() {
          chatGptTime = stopwatch.elapsed;
        });

        stopwatch..reset()..start();
        await _playTTS(responseText);
        stopwatch.stop();
        setState(() {
          ttsTime = stopwatch.elapsed;
        });

        setState(() {
          messages.add({'role': 'assistant', 'content': responseText});
          isLoading = false;
        });
      } else {
        setState(() {
          isLoading = false;
        });
      }
    } else {
      setState(() {
        isLoading = false;
      });
    }
  }

  Future<String?> _transcribeVoice(File audioFile) async {
    final url = Uri.parse('https://api.openai.com/v1/audio/transcriptions');

    try {
      final request = http.MultipartRequest('POST', url);

      request.headers['Authorization'] = 'Bearer $apiKey';
      request.files.add(await http.MultipartFile.fromPath(
        'file',
        audioFile.path,
        contentType: MediaType('audio', 'wav'),
      ));

      request.fields['model'] = 'whisper-1';
      request.fields['language'] = 'fa';

      final response = await request.send();

      if (response.statusCode == 200) {
        final responseBytes = await response.stream.toBytes();
        final responseString = utf8.decode(responseBytes);
        final decoded = json.decode(responseString);

        return decoded['text'];
      } else {
        final errorBytes = await response.stream.toBytes();
        final errorString = utf8.decode(errorBytes, allowMalformed: true);
        print('Error response: $errorString');
        return null;
      }
    } catch (e) {
      print('Error in Whisper STT: $e');
      return null;
    }
  }

  Future<String> _sendTextToChatAPI(String text) async {
    final url = Uri.parse('https://api.openai.com/v1/chat/completions');

    try {
      final response = await http.post(
        url,
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $apiKey',
        },
        body: json.encode({
          'model': 'gpt-4',
          'messages': [
            {'role': 'system', 'content': 'You are a helpful assistant.'},
            {'role': 'user', 'content': text}
          ],
          'max_tokens': 200,
          'temperature': 0.7,
        }),
      );

      if (response.statusCode == 200) {
        final responseData = json.decode(utf8.decode(response.bodyBytes));
        return responseData['choices'][0]['message']['content'].toString();
      } else {
        throw Exception('Error in ChatGPT response: ${response.body}');
      }
    } catch (e) {
      print('Error in ChatGPT API call: $e');
      return 'An error occurred.';
    }
  }

  Future<void> _playTTS(String text) async {
    final url = Uri.parse('https://api.openai.com/v1/audio/speech');

    try {
      final requestBody = {
        "model": "tts-1-hd",
        "input": text,
        "voice": "nova",
      };

      final response = await http.post(
        url,
        headers: {
          'Authorization': 'Bearer $apiKey',
          'Content-Type': 'application/json',
        },
        body: json.encode(requestBody),
      );

      if (response.statusCode == 200) {
        final audioData = response.bodyBytes;

        // Play the audio data
        await _player.startPlayer(fromDataBuffer: audioData, codec: Codec.mp3);
      } else {
        print('Error response from TTS: ${utf8.decode(response.bodyBytes)}');
      }
    } catch (e) {
      print('Error during TTS API call: $e');
    }
  }

  @override
  void dispose() {
    _recorder.closeRecorder();
    _player.closePlayer();
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
          if (isChatOpen) // Chatbox on the left side
            Positioned(
              top: 20,
              left: 20,
              bottom: 80, // Adjusted to make space for the footer
              width: MediaQuery.of(context).size.width * 0.7,
              child: ClipRRect(
                borderRadius:
                    BorderRadius.circular(16.0), // Add rounded corners
                child: Container(
                  color: const Color(0xFF1E1E2C),
                  child: Column(
                    children: [
                      Expanded(
                        child: ListView.builder(
                          reverse: true,
                          itemCount: messages.length,
                          itemBuilder: (context, index) {
                            final message =
                                messages[messages.length - 1 - index];
                            final isUser = message['role'] == 'user';
                            return Container(
                              margin: const EdgeInsets.symmetric(
                                  vertical: 8.0, horizontal: 16.0),
                              alignment: isUser
                                  ? Alignment.centerRight
                                  : Alignment.centerLeft,
                              child: Container(
                                padding: const EdgeInsets.all(12.0),
                                decoration: BoxDecoration(
                                  color:
                                      isUser ? Colors.blue : Colors.grey[300],
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                child: Text(
                                  message['content'] ?? '',
                                  style: TextStyle(
                                    fontSize: 16,
                                    color: isUser ? Colors.white : Colors.black,
                                  ),
                                ),
                              ),
                            );
                          },
                        ),
                      ),
                      if (isLoading)
                        const Padding(
                          padding: EdgeInsets.all(8.0),
                          child: CircularProgressIndicator(),
                        ),
                    ],
                  ),
                ),
              ),
            ),
          Positioned(
            bottom: 24,
            right: 16,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.end,
              children: [
                if (!isRecording)
                  Container(
                    margin: const EdgeInsets.only(bottom: 8),
                    padding:
                        const EdgeInsets.symmetric(vertical: 8, horizontal: 12),
                    decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.circular(8),
                    ),
                    child: const Text(
                      'چطور میتونم کمکت کنم؟',
                      style: TextStyle(fontSize: 14, color: Colors.black),
                    ),
                  ),
                GestureDetector(
                  onTap: () async {
                    if (isRecording) {
                      await _stopRecordingAndProcess();
                    } else {
                      await _startRecording();
                    }
                  },
                  child: CircleAvatar(
                    radius: 40,
                    backgroundColor: isRecording ? Colors.red : Colors.blueGrey,
                    child: Padding(
                      padding: const EdgeInsets.all(4.0),
                      child: Image.asset(
                        'assets/robot.png',
                        fit: BoxFit.contain,
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
          // Footer to display API response times
          Positioned(
            bottom: 0,
            left: 0,
            right: 0,
            child: Container(
              height: 50,
              color: Colors.black.withOpacity(0.7),
              padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children: [
                  Text(
                    'Whisper: ${whisperTime.inMilliseconds / 1000}s',
                    style: const TextStyle(color: Colors.white, fontSize: 14),
                  ),
                  Text(
                    'ChatGPT: ${chatGptTime.inMilliseconds / 1000}s',
                    style: const TextStyle(color: Colors.white, fontSize: 14),
                  ),
                  Text(
                    'TTS: ${ttsTime.inMilliseconds / 1000}s',
                    style: const TextStyle(color: Colors.white, fontSize: 14),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}