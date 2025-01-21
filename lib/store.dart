import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:audioplayers/audioplayers.dart';
import 'package:flutter_sound/flutter_sound.dart';
import 'package:permission_handler/permission_handler.dart';
import 'dart:io';
import 'package:http_parser/http_parser.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'دستیار صوتی',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        brightness: Brightness.light,
        primaryColor: Colors.teal,
        scaffoldBackgroundColor: Colors.grey.shade100,
        appBarTheme: const AppBarTheme(
          backgroundColor: Colors.teal,
          foregroundColor: Colors.white,
          elevation: 4,
        ),
        textTheme: const TextTheme(
          bodyLarge: TextStyle(fontSize: 22, fontWeight: FontWeight.w500),
          bodyMedium: TextStyle(fontSize: 20, fontWeight: FontWeight.w400),
          bodySmall: TextStyle(fontSize: 18, fontWeight: FontWeight.w300),
        ),
        elevatedButtonTheme: ElevatedButtonThemeData(
          style: ElevatedButton.styleFrom(
            backgroundColor: Colors.teal,
            foregroundColor: Colors.white,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(16),
            ),
            elevation: 4,
          ),
        ),
        iconTheme: const IconThemeData(color: Colors.teal, size: 32),
      ),
      home: const Directionality(
        textDirection: TextDirection.rtl,
        child: TextToSpeechPage(),
      ),
    );
  }
}

class TextToSpeechPage extends StatefulWidget {
  const TextToSpeechPage({super.key});

  @override
  _TextToSpeechPageState createState() => _TextToSpeechPageState();
}

class _TextToSpeechPageState extends State<TextToSpeechPage> {
  late AudioPlayer audioPlayer;
  String tokenForTTS = '';
  String tokenForSTT = '';

  @override
  void initState() {
    super.initState();
    audioPlayer = AudioPlayer();
    _fetchTokenForTTS();
    _fetchTokenForSTT();
  }

  Future<void> _fetchTokenForTTS() async {
    final url = Uri.parse('http://188.121.113.116:8006/token');
    try {
      final response = await http.post(
        url,
        headers: {
          'accept': 'application/json',
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: {
          'grant_type': 'password',
          'username': 'admin',
          'password': 'Y4Sust/Gwq,9?+,35{gh',
        },
      );
      if (response.statusCode == 200) {
        final responseData = json.decode(response.body);
        setState(() {
          tokenForTTS = responseData['access_token'];
        });
      } else {
        throw Exception('خطا در دریافت توکن TTS: ${response.body}');
      }
    } catch (e) {
      print('خطا در دریافت توکن TTS: $e');
    }
  }

  Future<void> _fetchTokenForSTT() async {
    final url = Uri.parse('http://188.121.113.116:8000/token');
    try {
      final response = await http.post(
        url,
        headers: {
          'accept': 'application/json',
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: {
          'grant_type': 'password',
          'username': 'hinava',
          'password': 'AO5!JGlEsOl1;MP',
        },
      );
      if (response.statusCode == 200) {
        final responseData = json.decode(response.body);
        setState(() {
          tokenForSTT = responseData['access_token'];
        });
      } else {
        throw Exception('خطا در دریافت توکن STT: ${response.body}');
      }
    } catch (e) {
      print('خطا در دریافت توکن STT: $e');
    }
  }

  void _navigateToVoicePage() {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => VoiceInteractionPage(
          tokenForTTS: tokenForTTS,
          tokenForSTT: tokenForSTT,
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('دستیار صوتی'),
      ),
      body: Center(
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 16.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Text(
                'به دستیار صوتی خوش آمدید',
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                  color: Colors.teal,
                ),
                textAlign: TextAlign.center,
              ),
              const SizedBox(height: 16),
              ElevatedButton.icon(
                onPressed: _navigateToVoicePage,
                icon: const Icon(Icons.mic),
                label: const Text('شروع'),
                style: ElevatedButton.styleFrom(
                  padding:
                      const EdgeInsets.symmetric(horizontal: 32, vertical: 12),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  @override
  void dispose() {
    audioPlayer.dispose();
    super.dispose();
  }
}

class VoiceInteractionPage extends StatefulWidget {
  final String tokenForTTS;
  final String tokenForSTT;

  const VoiceInteractionPage({
    super.key,
    required this.tokenForTTS,
    required this.tokenForSTT,
  });

  @override
  _VoiceInteractionPageState createState() => _VoiceInteractionPageState();
}

class _VoiceInteractionPageState extends State<VoiceInteractionPage> {
  late AudioPlayer audioPlayer;
  late FlutterSoundRecorder recorder;
  bool isRecording = false;
  final List<Map<String, dynamic>> messages = [];
  final ScrollController _scrollController = ScrollController();

  @override
  void initState() {
    super.initState();
    audioPlayer = AudioPlayer();
    recorder = FlutterSoundRecorder();
    _initializeRecorder();
    _playTTS('سلام. چطور می‌توانم کمک کنم؟').then((_) {
      _addMessage('AI', 'سلام. چطور می‌توانم کمک کنم؟');
    });
  }

  Future<void> _initializeRecorder() async {
    if (await Permission.microphone.request().isGranted) {
      await recorder.openRecorder();
    } else {
      print('مجوز میکروفون داده نشد');
    }
  }

  void _addMessage(String sender, dynamic message, {bool isLoading = false}) {
    setState(() {
      messages
          .add({'sender': sender, 'message': message, 'isLoading': isLoading});
    });
    Future.delayed(const Duration(milliseconds: 200), () {
      _scrollController.animateTo(
        _scrollController.position.maxScrollExtent,
        duration: const Duration(milliseconds: 300),
        curve: Curves.easeOut,
      );
    });
  }

  Future<void> _playTTS(String text) async {
    final url = Uri.parse('https://api.openai.com/v1/audio/speech');
    await dotenv.load(fileName: ".env");
    final String apiKey = dotenv.env['GPT_API_KEY'] ?? '';

    try {
      // Prepare the request body
      final requestBody = {
        "model": "tts-1-hd",
        "input": text,
        "voice": "nova" // Choose a supported voice from OpenAI
      };

      // Make a POST request to OpenAI TTS API
      final response = await http.post(
        url,
        headers: {
          'Authorization': 'Bearer $apiKey',
          'Content-Type': 'application/json',
        },
        body: json.encode(requestBody),
      );

      if (response.statusCode == 200) {
        // Save the audio data
        final audioData = response.bodyBytes;

        // Play the received audio data
        await audioPlayer.setSourceBytes(audioData);
        await audioPlayer.resume();
      } else {
        // Log error details from the response
        print('Error response from TTS: ${utf8.decode(response.bodyBytes)}');
      }
    } catch (e) {
      // Handle exceptions during the API call
      print('Error during TTS API call: $e');
    }
  }

  Future<void> _startRecording() async {
    if (await Permission.microphone.request().isGranted) {
      const filePath = 'temp_audio.wav';
      await recorder.startRecorder(toFile: filePath, codec: Codec.pcm16WAV);
      setState(() {
        isRecording = true;
      });
    } else {
      print('مجوز میکروفون داده نشد');
    }
  }

  Future<void> _stopRecording(String filePath) async {
    final recordedFile = await recorder.stopRecorder();
    setState(() {
      isRecording = false;
    });

    if (recordedFile != null) {
      final sttText = await _sendAudioToSTT(File(recordedFile));
      if (sttText.isNotEmpty) {
        _addMessage('کاربر', sttText);
        _addMessage('AI', '...', isLoading: true);

        // Show loading while waiting for GPT-4 response
        final chatResponse = await _sendTextToChatAPI(sttText);

        if (chatResponse.isNotEmpty) {
          // Play AI response
          await _playTTS(chatResponse).then((_) {
            setState(() {
              messages.removeLast(); // Remove loading indicator
            });
            _addMessage('AI', chatResponse);
          });
        } else {
          setState(() {
            messages.removeLast(); // Remove loading indicator
          });
        }
      }
    }
  }

  Future<String> _sendAudioToSTT(File audioFile) async {
    final url = Uri.parse('https://api.openai.com/v1/audio/transcriptions');
    await dotenv.load(fileName: ".env");
    final String apiKey = dotenv.env['GPT_API_KEY'] ?? '';

    try {
      final request = http.MultipartRequest('POST', url);

      request.headers['Authorization'] = 'Bearer $apiKey';
      request.files.add(await http.MultipartFile.fromPath(
        'file',
        audioFile.path,
        contentType: MediaType('audio', 'wav'),
      ));

      // Optionally specify Whisper model and response language
      request.fields['model'] = 'whisper-1'; // OpenAI's Whisper model
      request.fields['language'] = 'fa'; // Persian transcription (optional)

      final response = await request.send();

      if (response.statusCode == 200) {
        // Read bytes from the response stream
        final responseBytes = await response.stream.toBytes();
        // Decode the response as UTF-8
        final responseString = utf8.decode(responseBytes);
        final decoded = json.decode(responseString);

        return decoded['text'] ?? '';
      } else {
        // Handle error
        final errorBytes = await response.stream.toBytes();
        final errorString = utf8.decode(errorBytes, allowMalformed: true);
        print('Error response: $errorString');
        throw Exception('Error in Whisper response: ${response.statusCode}');
      }
    } catch (e) {
      print('Error in Whisper STT: $e');
      return '';
    }
  }

  Future<String> _sendTextToChatAPI(String text) async {
    final url = Uri.parse('https://api.openai.com/v1/chat/completions');

    await dotenv.load(fileName: ".env");
    final String apiKey = dotenv.env['GPT_API_KEY'] ?? '';

    try {
      final response = await http.post(
        url,
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $apiKey',
        },
        body: json.encode({
          'model': 'gpt-4o', // Use GPT-4 model
          'messages': [
            {
              'role': 'system',
              'content':
                  'You are a helpful assistant who can also respond in Persian.'
            },
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
      print('Error: $e');
      return 'An error occurred.';
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('دستیار صوتی'),
      ),
      body: Column(
        children: [
          Expanded(
            child: ListView.builder(
              controller: _scrollController,
              itemCount: messages.length,
              padding: const EdgeInsets.symmetric(vertical: 10),
              itemBuilder: (context, index) {
                final message = messages[index];
                final isAI = message['sender'] == 'AI';
                final isLoading = message['isLoading'] ?? false;

                return Align(
                  alignment:
                      isAI ? Alignment.centerLeft : Alignment.centerRight,
                  child: Container(
                    margin:
                        const EdgeInsets.symmetric(vertical: 5, horizontal: 10),
                    padding: const EdgeInsets.all(12),
                    constraints: const BoxConstraints(maxWidth: 250),
                    decoration: BoxDecoration(
                      color: isAI ? Colors.teal.shade50 : Colors.blue.shade50,
                      borderRadius: BorderRadius.circular(16),
                    ),
                    child: isLoading
                        ? const Row(
                            children: [
                              CircularProgressIndicator(),
                              SizedBox(width: 10),
                              Text('در حال پردازش...'),
                            ],
                          )
                        : Text(
                            message['message'],
                            style: TextStyle(
                              fontSize: 16,
                              color:
                                  isAI ? Colors.black87 : Colors.blue.shade900,
                            ),
                          ),
                  ),
                );
              },
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                FloatingActionButton(
                  onPressed: isRecording ? null : _startRecording,
                  backgroundColor: isRecording ? Colors.grey : Colors.teal,
                  child: Icon(
                    Icons.mic,
                    color: isRecording ? Colors.red : Colors.white,
                  ),
                ),
                const SizedBox(width: 20),
                ElevatedButton(
                  onPressed: isRecording
                      ? () {
                          const filePath = 'temp_audio.wav';
                          _stopRecording(filePath);
                        }
                      : null,
                  child: const Padding(
                    padding: EdgeInsets.symmetric(horizontal: 24, vertical: 12),
                    child: Text('ارسال و پردازش'),
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  @override
  void dispose() {
    audioPlayer.dispose();
    recorder.closeRecorder();
    super.dispose();
  }
}
