package dev.tanakornsss.luminality.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notificationHandler = NotificationHandler(context)
        notificationHandler.showNotification(
            "How it is going today?",
            "Don't forget to appreciate yourself!"
        )
    }
}
