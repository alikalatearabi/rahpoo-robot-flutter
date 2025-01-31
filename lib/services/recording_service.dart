import 'package:flutter_sound/flutter_sound.dart';
import 'package:path_provider/path_provider.dart';
import 'package:permission_handler/permission_handler.dart';

class RecordingService {
  final FlutterSoundRecorder _recorder = FlutterSoundRecorder();
  String? recordedFilePath;

  Future<void> initializeRecorder() async {
    final status = await Permission.microphone.request();
    if (status != PermissionStatus.granted) {
      throw RecordingPermissionException('Microphone permission not granted');
    }
    await _recorder.openRecorder();
  }

  Future<String?> startRecording() async {
    final tempDir = await getTemporaryDirectory();
    final filePath = '${tempDir.path}/voice_recording.wav';

    await _recorder.startRecorder(
      toFile: filePath,
      codec: Codec.pcm16WAV,
    );

    recordedFilePath = filePath;
    return filePath;
  }

  Future<void> stopRecording() async {
    await _recorder.stopRecorder();
  }

  void dispose() {
    _recorder.closeRecorder();
  }
}
