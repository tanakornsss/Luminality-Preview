package dev.tanakornsss.luminality.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import dev.tanakornsss.luminality.BuildConfig

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notificationHandler = NotificationHandler(context)

        if (BuildConfig.LOGGING) {
            val tag = "NotifReceiver"
            Log.d(tag, "onReceive called at ${System.currentTimeMillis()}")
            Toast.makeText(context, "Receiver fired!", Toast.LENGTH_LONG).show()
        }

        notificationHandler.showNotification(
            "How it is going today?",
            "Don't forget to appreciate yourself!"
        )
    }
}
