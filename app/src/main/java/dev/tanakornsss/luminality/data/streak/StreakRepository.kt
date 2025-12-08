package dev.tanakornsss.luminality.data.streak

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.util.Calendar

class StreakRepository(private val streakDao: StreakDao) {
    val streakFlow = streakDao.getStreak()

    suspend fun updateStreak(today: Long) = withContext(Dispatchers.IO) {
        // Check if streak is meant to be added
        // If its the first time of the day, then add the streak
        // If not, nothing happens
        // Streak will not be reset

        val streak = streakDao.getStreak()

        val data = streak.first()
        val lastDate = data.lastUsedDate
        val currentStreak = data.currentStreak

        if (isSameDay(lastDate, today)) return@withContext

        if (isYesterday(lastDate, today)) {
            val new = Streak(
                currentStreak = currentStreak + 1,
                lastUsedDate = today
            )
            streakDao.updateStreak(new)
            return@withContext
        }
    }
}

private fun isSameDay(last: Long, today: Long): Boolean {
    val cLast = Calendar.getInstance().apply { timeInMillis = last }
    val cToday = Calendar.getInstance().apply { timeInMillis = today }

    return cLast.get(Calendar.YEAR) == cToday.get(Calendar.YEAR) &&
            cLast.get(Calendar.DAY_OF_YEAR) == cToday.get(Calendar.DAY_OF_YEAR)
}

private fun isYesterday(last: Long, today: Long): Boolean {
    val cLast = Calendar.getInstance().apply { timeInMillis = last }
    val cToday = Calendar.getInstance().apply { timeInMillis = today }
    cLast.add(Calendar.DAY_OF_YEAR, 1)

    return isSameDay(cLast.timeInMillis, cToday.timeInMillis)
}
