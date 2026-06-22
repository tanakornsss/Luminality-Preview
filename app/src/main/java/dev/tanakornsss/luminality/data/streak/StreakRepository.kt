package dev.tanakornsss.luminality.data.streak

interface StreakRepository {
    suspend fun updateStreak(today: Long)
}