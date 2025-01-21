package com.example.smart_robot

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import mc.csst.com.selfchassislibrary.chassis.SelfChassis

class MainActivity : FlutterActivity() {
    private val CHANNEL = "com.example.robot"
    private val chassis = SelfChassis.getInstance()

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            when (call.method) {
                "connectChassis" -> {
                    val url = call.argument<String>("url")
                    if (url != null) {
                        chassis.connectSelfChassis(url)
                        result.success("Connected to chassis at: $url")
                    } else {
                        result.error("INVALID_URL", "URL is null", null)
                    }
                }
                "moveToMarker" -> {
                    val markerName = call.argument<String>("markerName")
                    if (markerName != null) {
                        chassis.sendMoveByMarkerName(markerName)
                        result.success("Moving to marker: $markerName")
                    } else {
                        result.error("INVALID_MARKER", "Marker name is null", null)
                    }
                }
                "getRobotStatus" -> {
                    chassis.sendGetRobotStatus()
                    result.success("Requested robot status")
                }
                "setVelocity" -> {
                   val angularSpeed = call.argument<Double>("z")?.toFloat() ?: 0f
                   val linearSpeed = call.argument<Double>("x")?.toFloat() ?: 0f
                   chassis.setVelocity(angularSpeed, linearSpeed)
                   result.success("Velocity set: z=$angularSpeed, x=$linearSpeed")
                }
                else -> {
                    result.notImplemented()
                }
            }
        }
    }
}
