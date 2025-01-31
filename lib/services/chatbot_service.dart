import 'dart:convert';
import 'package:http/http.dart' as http;

class ChatbotService {
  static const String _chatbotUrl = "http://79.127.12.135:5600/chatbot_simple";

  static Future<Map<String, String>?> getChatbotResponse(String query, String conversationId) async {
    try {
      final response = await http.post(
        Uri.parse(_chatbotUrl),
        headers: {"Content-Type": "application/json"},
        body: jsonEncode({
          "query": query,
          "conversation_id": conversationId,
        }),
      );

      if (response.statusCode == 200) {
        final responseData = json.decode(utf8.decode(response.bodyBytes));
        return {
          "answer": responseData["answer"],
          "conversation_id": responseData["conversation_id"],
        };
      } else {
        print("Failed to get AI response: ${response.statusCode}");
      }
    } catch (e) {
      print("Error calling chatbot API: $e");
    }
    return null;
  }
}
