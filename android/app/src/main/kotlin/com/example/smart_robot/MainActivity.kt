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

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
                call,
                result ->
            when (call.method) {
                "checkAndMove" -> {
                    val url = call.argument<String>("url")
                    val markerName = call.argument<String>("markerName")
                    if (url != null && markerName != null) {
                        checkAndMove(url, markerName, result)
                    } else {
                        result.error("INVALID_ARGUMENTS", "URL or Marker name is null", null)
                    }
                }
                else -> {
                    result.notImplemented()
                }
            }
        }
    }

    /** Checks the connection, establishes it if necessary, and sends the move command. */
    private fun checkAndMove(url: String, markerName: String, result: MethodChannel.Result) {
        val maxRetries = 3 // Define the maximum number of retries
        val retryDelayMillis = 5000L // Delay between retries (in milliseconds)
        var retryCount = 0

        fun attemptConnection() {
            if (retryCount >= maxRetries) {
                result.error(
                        "CONNECTION_FAILED",
                        "Failed to connect after $maxRetries attempts",
                        null
                )
                return
            }

            println("Attempting to connect to WebSocket... (Attempt ${retryCount + 1})")
            chassis.connectSelfChassis(url)

            // Step 3: Monitor connection state and send the command once connected
            chassis.setOnMessageReceivedCallBack(
                    object : SelfChassis.OnMessageReceivedCallBack {
                        override fun messageReceive(jsonStr: String) {
                            // Handle any received messages if necessary
                        }

                        override fun messageConnect(isConnected: Boolean) {
                            if (isConnected) {
                                println("WebSocket connected successfully!")
                                sendMoveCommand(markerName, result)
                            } else {
                                println("Connection failed. Retrying...")
                                retryCount++
                                Thread.sleep(retryDelayMillis) // Wait before retrying
                                attemptConnection() // Retry connection
                            }
                        }

                        override fun close() {
                            println("WebSocket connection closed.")
                        }
                    }
            )
        }

        attemptConnection() // Start the connection attempt
    }

    /** Sends the move command to the specified marker. */
    private fun sendMoveCommand(markerName: String, result: MethodChannel.Result) {
        try {
            chassis.sendMoveByMarkerName(markerName)
            result.success("Moving to marker: $markerName")
        } catch (e: Exception) {
            result.error("MOVE_FAILED", "Failed to move to marker: ${e.localizedMessage}", null)
        }
    }
}
