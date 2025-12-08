package dev.tanakornsss.luminality.data.streak

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StreakDao {
    @Query("SELECT * FROM streak_table WHERE id = 1")
    fun getStreak(): Flow<Streak?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateStreak(streak: Streak)
}