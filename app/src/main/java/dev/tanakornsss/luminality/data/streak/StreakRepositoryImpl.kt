package dev.tanakornsss.luminality.data.streak

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.ZoneId

class StreakRepositoryImpl(private val streakDao: StreakDao): StreakRepository {
    val streakFlow = streakDao.getStreak().map { streak ->
        streak ?: Streak(
            id = 1,
            currentStreak = 0,
            lastUsedDate = 0
        )
    }

    override suspend fun updateStreak(today: Long) = withContext(Dispatchers.IO) {
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
        val lastDate = lastDateInstant.atZone(zone).toLocalDate()
        val todayDate = todayInstant.atZone(zone).toLocalDate()

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
