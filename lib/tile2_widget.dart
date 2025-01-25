import 'dart:typed_data';

import 'package:flutter/material.dart';

class Tile1Widget extends StatelessWidget {
  const Tile1Widget({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Map View'),
      ),
      body: Center(
        child: FutureBuilder<Map<String, dynamic>>(
          future: _fetchMapData(),
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.waiting) {
              return const CircularProgressIndicator();
            } else if (snapshot.hasError) {
              return Text('Error: ${snapshot.error}');
            } else if (!snapshot.hasData) {
              return const Text('No map data available');
            } else {
              // Display the map using an Image widget
              return Image.memory(snapshot.data!['mapData']);
            }
          },
        ),
      ),
    );
  }

  Future<Map<String, dynamic>> _fetchMapData() async {
    // Simulate fetching map data
    await Future.delayed(const Duration(seconds: 2));
    return {
      'mapData': Uint8List.fromList([]), // Replace with actual map data
    };
  }
}