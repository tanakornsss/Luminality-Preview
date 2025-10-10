package dev.tanakornsss.luminality.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import java.util.Calendar

object NotificationScheduler {
    private const val TAG = "NotifSched"

    @RequiresApi(Build.VERSION_CODES.S)
    fun scheduleDailyNotification(context: Context) {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent =
            Intent(context, NotificationReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

//        val calendar = Calendar.getInstance().apply {
//            timeInMillis = System.currentTimeMillis()
//            set(Calendar.HOUR_OF_DAY, 20)
//            set(Calendar.MINUTE, 6)
//            set(Calendar.SECOND, 0)
//        }

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            add(Calendar.MINUTE, 1) // Test
        }

        Log.d(TAG, "Scheduling alarm for: ${calendar.time} (ms=${calendar.timeInMillis})")
        Toast.makeText(context, "Scheduling alarm for: ${calendar.time}", Toast.LENGTH_SHORT).show()

//        if (calendar.timeInMillis < System.currentTimeMillis()) {
//            calendar.add(Calendar.DAY_OF_YEAR, 1)
//        }

        if (!alarmManager.canScheduleExactAlarms()) {
            val intent = Intent().apply {
                action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                data = ("package:" + context.packageName).toUri()
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            Log.d(TAG, "Requesting exact alarm permission UI")
            context.startActivity(intent)
            return
        }
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
        Log.d(TAG, "Alarm setExactAndAllowWhileIdle called")
        Toast.makeText(context, "Alarm set (1 min test)", Toast.LENGTH_SHORT).show()
    }
}
