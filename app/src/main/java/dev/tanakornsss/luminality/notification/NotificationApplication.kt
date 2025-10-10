package dev.tanakornsss.luminality.notification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

const val CHANNEL_ID = "scheduled_notification_channel"
const val CHANNEL_NAME = "Scheduled Notification Channel"

class NotificationApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val scheduledNotificationChannel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )

        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(scheduledNotificationChannel)
    }
}
