import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
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
                    // Card 1
                    _buildCard(
                      "01",
                      "END TO END DEVOPS AND SECURITY FOR AWS AND GCP",
                      const Color(0xFFEEEEEE),
                    ),
                    // Card 2
                    _buildCard(
                      "02",
                      "EXPERT KUBERNETES SETUP AND MAINTENANCE",
                      const Color(0xFF0A0E1A),
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
                    "Scale your business with a reliable and efficient IT-infrastructure",
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
      ),
    );
  }

  Widget _buildCard(String number, String title, Color textColor) {
    return Container(
      decoration: BoxDecoration(
        color: textColor == const Color(0xFFEEEEEE)
            ? Colors.black
            : const Color(0xFFEEEEEE),
        borderRadius: BorderRadius.circular(8.0),
      ),
      padding: const EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            number,
            style: TextStyle(
              fontSize: 20,
              fontWeight: FontWeight.bold,
              color: textColor,
            ),
          ),
          const SizedBox(height: 8),
          Text(
            title,
            style: TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.w500,
              color: textColor,
            ),
          ),
          const Spacer(),
          TextButton(
            onPressed: () {},
            style: TextButton.styleFrom(
              backgroundColor: textColor == const Color(0xFFEEEEEE)
                  ? const Color(0xFFFF7645)
                  : Colors.black,
              padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
            ),
            child: const Text(
              "JOIN US",
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
