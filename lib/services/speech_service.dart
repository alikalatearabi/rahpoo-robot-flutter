import 'dart:convert';
import 'package:http/http.dart' as http;

class SpeechService {
  static const String _apiUrl = "http://188.121.113.116:8000/speech_recognition/";

  static Future<String?> transcribeAudio(String filePath, String accessToken) async {
    try {
      var request = http.MultipartRequest("POST", Uri.parse(_apiUrl))
        ..headers.addAll({
          "Authorization": "Bearer $accessToken",
          "Accept": "application/json",
        })
        ..files.add(await http.MultipartFile.fromPath("file", filePath));

      var response = await request.send();
      if (response.statusCode == 200) {
        final responseData = await response.stream.bytesToString();
        final jsonResponse = json.decode(responseData);
        return jsonResponse["results"];
      } else {
        print("Failed to transcribe audio: ${response.statusCode}");
      }
    } catch (e) {
      print("Error sending voice file: $e");
    }
    return null;
  }
}
