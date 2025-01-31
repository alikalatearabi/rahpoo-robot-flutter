import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:web_socket_channel/web_socket_channel.dart';

class RobotMovePage extends StatefulWidget {
  @override
  _RobotMovePageState createState() => _RobotMovePageState();
}

class _RobotMovePageState extends State<RobotMovePage> {
  late WebSocketChannel channel;
  String connectionStatus = "Not connected";
  String lastMessage = "No data received";
  bool isRegistered = false;

  @override
  void initState() {
    super.initState();
    _connectWebSocket();
  }

  void _connectWebSocket() {
    try {
      channel = WebSocketChannel.connect(
        Uri.parse('ws://192.168.20.22:9090'),
      );

      setState(() {
        connectionStatus = "Connected to WebSocket";
      });

      // Listen for messages from the WebSocket
      channel.stream.listen(
        (message) {
          final data = jsonDecode(message);
          setState(() {
            lastMessage = "Message received: $data";
          });
          print("Message from WebSocket: $data");
        },
        onError: (error) {
          setState(() {
            connectionStatus = "Error: $error";
          });
          print("WebSocket Error: $error");
        },
        onDone: () {
          setState(() {
            connectionStatus = "Connection closed";
          });
          print("WebSocket connection closed");
        },
      );

      // Register the topic (advertise)
      _registerTopic();
    } catch (e) {
      setState(() {
        connectionStatus = "Failed to connect: $e";
      });
      print("WebSocket Connection Error: $e");
    }
  }

  void _registerTopic() {
    final advertiseCommand = {
      "args": {"poi": "Charger"},
      "id": "service_poi",
      "service": "/poi",
      "op": "call_service"
    };

    channel.sink.add(jsonEncode(advertiseCommand));
    setState(() {
      connectionStatus = "Topic registered for publishing";
      isRegistered = true;
    });
    print("Sent Advertise Command: ${jsonEncode(advertiseCommand)}");
  }

  void _moveToMarker(String markerName) {
    if (!isRegistered) {
      setState(() {
        connectionStatus = "Topic not registered. Cannot send command.";
      });
      return;
    }

    final publishCommand = {
      "op": "publish",
      "id": "move_to_marker",
      "topic": "/move_base",
      "msg": {
        "goal": {
          "target_marker": "Charger",
        }
      }
    };

    channel.sink.add(jsonEncode(publishCommand));
    setState(() {
      connectionStatus = "Command sent to move to $markerName";
    });
    print("Sent Publish Command: ${jsonEncode(publishCommand)}");
  }

  @override
  void dispose() {
    channel.sink.close();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Robot Marker Navigation'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Text(
              "Connection Status: $connectionStatus",
              style: TextStyle(fontSize: 16),
              textAlign: TextAlign.center,
            ),
            SizedBox(height: 20),
            // Text(
            //   "Last Message: $lastMessage",
            //   style: TextStyle(fontSize: 14),
            //   textAlign: TextAlign.center,
            // ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                _moveToMarker("Charger");
              },
              child: Text('Move to Charger'),
            ),
          ],
        ),
      ),
    );
  }
}
