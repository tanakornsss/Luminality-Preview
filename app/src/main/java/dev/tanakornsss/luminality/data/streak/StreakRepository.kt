package dev.tanakornsss.luminality.data.streak

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.Calendar

class StreakRepository(private val streakDao: StreakDao) {
    val streakFlow = streakDao.getStreak().map { streak ->
        streak ?: Streak(
            id = 1,
            currentStreak = 0,
            lastUsedDate = 0
        )
    }

    suspend fun updateStreak(today: Long) = withContext(Dispatchers.IO) {
        // Check if streak is meant to be added
        // If its the first time of the day, then add the streak
        // If not, nothing happens
        // Streak will not be reset

        val data = streakFlow.first()
        val lastDate = data.lastUsedDate
        val currentStreak = data.currentStreak

        if (isSameDay(lastDate, today)) return@withContext

        if (isDayBefore(lastDate, today)) {
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

private fun isDayBefore(last: Long, today: Long): Boolean {
    val cLast = Calendar.getInstance().apply { timeInMillis = last }
    val cToday = Calendar.getInstance().apply { timeInMillis = today }

    val dateLast = cLast.get(Calendar.DAY_OF_YEAR)
    val dateToday = cToday.get(Calendar.DAY_OF_YEAR)

    return dateLast < dateToday
}
