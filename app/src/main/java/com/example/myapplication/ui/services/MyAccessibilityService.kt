package com.example.myapplication.ui.services

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import java.util.*

class MyAccessibilityService : AccessibilityService() {
    //whatsapp package name
    private val WHATSAPP_PACKAGE_NAME = "com.whatsapp"
    //TEXT to speech class object
    private lateinit var tts: TextToSpeech

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        /* check if event package name is same as that of whatsapp package name .
         If Yes then vibrate the phone and speak WhatsApp Launched */
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED &&
            event.packageName == WHATSAPP_PACKAGE_NAME) {
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(100)
            }
            tts.speak("WhatsApp Launched", TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    override fun onServiceConnected() {
        // Check if TTS data is available
        val intent = Intent(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)

        // Create a new instance of the TTS engine
        tts = TextToSpeech(applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts.setLanguage(Locale.getDefault())
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("MyAccessibilityService", "Language not supported")
                }
            } else {
                Log.e("MyAccessibilityService", "Failed to initialize TTS engine")
            }
        }
    }

    override fun onInterrupt() {
    }

    override fun onDestroy() {
        super.onDestroy()
        // Shutdown the TTS engine
        tts.stop()
        tts.shutdown()
    }

}