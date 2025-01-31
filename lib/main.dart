import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:smart_robot/RobotMovePage.dart';
import 'package:smart_robot/screens/tile2_widget.dart';
import 'tile1_widget.dart';

void main() async {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      debugShowCheckedModeBanner: false,
      home: MainPage(),
    );
  }
}

class MainPage extends StatelessWidget {
  const MainPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFF0A0E1A), // Background color
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Expanded(
              flex: 9,
              child: GridView.count(
                crossAxisCount: 2,
                mainAxisSpacing: 16,
                crossAxisSpacing: 16,
                childAspectRatio: 4 / 3,
                children: [
                  _buildCard(
                    "01",
                    "فرمان حرکتی",
                    const Color(0xFFEEEEEE),
                    Icons.navigation,
                    context,
                  ),
                  // Card 2
                  _buildCard(
                    "02",
                    "بیا با هم صحبت کنیم",
                    const Color(0xFF0A0E1A),
                    Icons.mic,
                    context,
                  ),
                  // Card 3
                  _buildCard(
                    "03",
                    "REFERENCE ARCHITECTURE DEPLOYMENTS",
                    const Color(0xFF0A0E1A),
                  ),
                  // Card 4
                  _buildCard(
                    "04",
                    "DATA PLATFORM AND PROCESSING INFRASTRUCTURE AND ENABLEMENT",
                    const Color(0xFFEEEEEE),
                  ),
                ],
              ),
            ),
            const SizedBox(height: 16),
            // Bottom Banner
            Expanded(
              flex: 1,
              child: Container(
                width: double.infinity,
                color: const Color(0xFFFF7645), // Banner color
                alignment: Alignment.center,
                child: const Text(
                  "Rahpoo",
                  style: TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.bold,
                    color: Colors.white,
                  ),
                  textAlign: TextAlign.center,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildCard(String number, String title, Color textColor,
      [IconData? icon, BuildContext? context]) {
    return Container(
      decoration: BoxDecoration(
        color: textColor == const Color(0xFFEEEEEE)
            ? Colors.black
            : const Color(0xFFEEEEEE),
        borderRadius: BorderRadius.circular(8.0),
      ),
      padding: const EdgeInsets.all(16.0),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center, // Center content
        crossAxisAlignment: CrossAxisAlignment.center, // Center horizontally
        children: [
          if (icon != null)
            Icon(
              icon,
              color: textColor,
              size: 40,
            ),
          const SizedBox(height: 8),
          Text(
            title,
            style: TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.w500,
              color: textColor,
              fontFamily: 'Roboto',
            ),
            textDirection: TextDirection.rtl, // Right-to-left text
            textAlign: TextAlign.center,
          ),
          const SizedBox(height: 16),
          TextButton(
            onPressed: () async {
              if (number == "01") {
                if (context != null) {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => const Tile1Widget()),
                  );
                }
              } else if (number == "02") {
                if (context != null) {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => const Tile2Widget()),
                  );
                }
              }
            },
            style: TextButton.styleFrom(
              backgroundColor: textColor == const Color(0xFFEEEEEE)
                  ? const Color(0xFFFF7645)
                  : Colors.black,
              padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
            ),
            child: const Text(
              "شروع",
              style: TextStyle(
                fontWeight: FontWeight.bold,
                color: Colors.white,
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class RobotChannel {
  static const platform = MethodChannel('com.example.robot');

  static Future<String> connectChassis(String url) async {
    try {
      final String result =
          await platform.invokeMethod('connectChassis', {'url': url});
      return result;
    } catch (e) {
      return 'Error: $e';
    }
  }

  static Future<void> moveToMarker(String markerName) async {
    try {
      await platform.invokeMethod('moveToMarker', {'markerName': markerName});
    } catch (e) {
      print('Error: $e');
    }
  }

  static Future<void> getRobotStatus() async {
    try {
      await platform.invokeMethod('getRobotStatus');
      print('Robot status requested');
    } catch (e) {
      print('Error: $e');
    }
  }

  static Future<void> setVelocity(double z, double x) async {
    try {
      await platform.invokeMethod('setVelocity', {'z': z, 'x': x});
    } catch (e) {
      print('Error setting velocity: $e');
    }
  }

  static Future<void> getMap() async {
    try {
      await platform.invokeMethod('getMap');
      print('Map requested');
    } catch (e) {
      print('Error: $e');
    }
  }
}
