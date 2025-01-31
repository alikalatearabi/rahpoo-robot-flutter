import 'package:flutter/material.dart';
import '../services/auth_service.dart';
import '../services/recording_service.dart';
import '../services/speech_service.dart';
import '../services/chatbot_service.dart';

class Tile2Widget extends StatefulWidget {
  const Tile2Widget({super.key});

  @override
  Tile2WidgetState createState() => Tile2WidgetState();
}

class Tile2WidgetState extends State<Tile2Widget> {
  final RecordingService _recordingService = RecordingService();
  String? accessToken;
  String transcribedText = "";
  String aiResponse = "";
  String conversationId = "";

  @override
  void initState() {
    super.initState();
    _initialize();
  }

  Future<void> _initialize() async {
    accessToken = await AuthService.fetchAuthToken();
    await _recordingService.initializeRecorder();
  }

  Future<void> _startRecording() async {
    setState(() => transcribedText = "");
    await _recordingService.startRecording();
  }

  Future<void> _stopRecording() async {
    await _recordingService.stopRecording();
    String? filePath = _recordingService.recordedFilePath;
    if (filePath != null && accessToken != null) {
      String? result = await SpeechService.transcribeAudio(filePath, accessToken!);
      if (result != null) {
        setState(() => transcribedText = result);
        await _sendToChatbot(result);
      }
    }
  }

  Future<void> _sendToChatbot(String text) async {
    var chatbotResponse = await ChatbotService.getChatbotResponse(text, conversationId);
    if (chatbotResponse != null) {
      setState(() {
        aiResponse = chatbotResponse["answer"]!;
        conversationId = chatbotResponse["conversation_id"]!;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFF0A0E1A),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          GestureDetector(
            onTap: () async {
              if (transcribedText.isEmpty) {
                await _startRecording();
              } else {
                await _stopRecording();
              }
            },
            child: const Icon(Icons.mic, size: 80, color: Colors.white),
          ),
          if (transcribedText.isNotEmpty)
            Text(transcribedText, style: const TextStyle(color: Colors.white)),
          if (aiResponse.isNotEmpty)
            Text(aiResponse, style: const TextStyle(color: Colors.green)),
        ],
      ),
    );
  }
}
