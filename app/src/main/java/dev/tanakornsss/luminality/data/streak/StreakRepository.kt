package dev.tanakornsss.luminality.data.streak

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StreakRepository(private val streakDao: StreakDao) {
    val streakFlow = streakDao.getStreakFlow()

    suspend fun updateStreak(today: Long) = withContext(Dispatchers.IO) {
        val streak = streakDao.getStreakOnce()

        if (streak == null) {
            streakDao.insertOrUpdate(
                Streak(
                    lastEntryDate = today,
                    currentStreak = 1,
                    longestStreak = 1
                )
            )
            return@withContext
        }

        val lastDate = streak.lastEntryDate

        val oneDay = 24 * 60 * 60 * 1000L

        val isYesterday = (today - lastDate) in oneDay..(oneDay + 10_000L)

        val isSameDay = (today - lastDate) < oneDay

        val newCurrentStreak =
            when {
                isSameDay -> streak.currentStreak
                isYesterday -> streak.currentStreak + 1
                else -> 1
            }

        val newLongest = maxOf(newCurrentStreak, streak.longestStreak)

        streakDao.insertOrUpdate(
            Streak(
                lastEntryDate = today,
                currentStreak = newCurrentStreak,
                longestStreak = newLongest
            )
        )
    }
}