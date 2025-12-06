package dev.tanakornsss.luminality.data.streak

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StreakDao {
    @Query("SELECT * FROM streak_table WHERE id = 1")
    suspend fun getStreak(): Streak?

    @Insert
    suspend fun insertOrUpdate(streak: Streak)
}