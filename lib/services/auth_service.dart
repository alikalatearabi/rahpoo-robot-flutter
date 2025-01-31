import 'dart:convert';
import 'package:http/http.dart' as http;

class AuthService {
  static const String _authUrl = "http://188.121.113.116:8000/token";

  static Future<String?> fetchAuthToken() async {
    try {
      final response = await http.post(
        Uri.parse(_authUrl),
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: {
          "username": "hinava",
          "password": "AO5!JGlEsOl1;MP",
        },
      );

      if (response.statusCode == 200) {
        final responseData = json.decode(response.body);
        return responseData["access_token"];
      } else {
        print("Failed to fetch access token: ${response.statusCode}");
      }
    } catch (e) {
      print("Error fetching token: $e");
    }
    return null;
  }
}
