package dev.tanakornsss.luminality.data.streak

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class StreakRepository(private val streakDao: StreakDao) {
    val streakFlow = streakDao.getStreak().map { streak ->
        streak ?: Streak(
            id = 1,
            currentStreak = 0,
            lastUsedDate = 0
        )
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    suspend fun updateStreak(today: Long) = withContext(Dispatchers.IO) {
        // Check if streak is meant to be added
        // If its the first time of the day, then add the streak
        // If not, nothing happens
        // Streak will not be reset

        val data = streakFlow.first()
        val last = data.lastUsedDate
        val currentStreak = data.currentStreak

        val lastDateInstant = Instant.ofEpochMilli(last)
        val todayInstant = Instant.ofEpochMilli(today)

        val zone = ZoneId.systemDefault()
        val lastDate = LocalDate.ofInstant(lastDateInstant, zone)
        val todayDate = LocalDate.ofInstant(todayInstant, zone)

        if (lastDate == todayDate) return@withContext

        if (lastDate.isBefore(todayDate)) {
            val new = Streak(
                currentStreak = currentStreak + 1,
                lastUsedDate = today
            )
            streakDao.updateStreak(new)
            return@withContext
        }
    }
}
