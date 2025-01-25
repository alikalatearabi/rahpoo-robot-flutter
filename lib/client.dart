import 'package:oauth2/oauth2.dart' as oauth2;
import 'package:http/http.dart' as http;

Future<oauth2.Client> getOAuth2Client() async {
  final tokenEndpoint = Uri.parse('http://188.121.113.116:8000/token'); 
  const username = 'hinava';
  const password = 'AO5!JGlEsOl1;MP';
  const clientId = 'JQuLqFJbJKXApiF5Pe2pyYly2gIUvbxFXolvyf5B'; // Replace with your client ID
  const clientSecret = 'erDHkG7ocokL2TjWug8PUBvsA9LE9zQPV0v3oHyxYgVcRdnpkwIfgxAirmMbU17q81bV3ayw51NUOzfzemhBWdqNDbZtkyF7BkzVwHy3UesJb8WsWDbeW0ZmqUNLaWPd'; // Replace with your client secret

  final client = await oauth2.resourceOwnerPasswordGrant(
    tokenEndpoint,
    username,
    password,
  );

  return client;
}