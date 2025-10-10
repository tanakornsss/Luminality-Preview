package dev.tanakornsss.luminality.notification

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import dev.tanakornsss.luminality.R
import kotlin.random.Random

class NotificationHandler(private val context: Context) {
    private val notificationManager =
        context.getSystemService(NotificationManager::class.java)

    fun showNotification(title: String, text: String) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(Random.nextInt(), notification)
    }
}
