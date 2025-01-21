import 'package:flutter/services.dart';

class RobotChannel {
  static const MethodChannel _channel = MethodChannel('com.example.selfchassislibrary');

  static Future<void> initializeRobot(String ip, int port) async {
    try {
      await _channel.invokeMethod('initializeRobot', {'ip': ip, 'port': port});
    } catch (e) {
      print("Failed to initialize robot: $e");
    }
  }

  static Future<void> sendCommand(String command) async {
    try {
      await _channel.invokeMethod('sendCommand', {'command': command});
    } catch (e) {
      print("Failed to send command: $e");
    }
  }

  static Future<String?> getRobotStatus() async {
    try {
      final String? status = await _channel.invokeMethod('getRobotStatus');
      return status;
    } catch (e) {
      print("Failed to get robot status: $e");
      return null;
    }
  }
}
