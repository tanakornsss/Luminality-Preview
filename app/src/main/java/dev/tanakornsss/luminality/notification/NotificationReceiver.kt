package dev.tanakornsss.luminality.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notificationHandler = NotificationHandler(context)

        val tag = "NotifReceiver"
        Log.d(tag, "onReceive called at ${System.currentTimeMillis()}")
        Toast.makeText(context, "Receiver fired!", Toast.LENGTH_LONG).show()

        notificationHandler.showNotification(
            "How it is going today?",
            "Don't forget to appreciate yourself!"
        )
    }
}
