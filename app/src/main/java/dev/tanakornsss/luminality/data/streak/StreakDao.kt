package dev.tanakornsss.luminality.data.streak

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StreakDao {
    @Query("SELECT * FROM streak_table WHERE id = 1")
    fun getStreak(): Flow<Streak?>

    @Insert(onConflict = REPLACE)
    suspend fun updateStreak(streak: Streak)

    @Query("SELECT * FROM streak_table WHERE id = 1")
    suspend fun exportAllStreak(): Streak

    @Insert(onConflict = REPLACE)
    suspend fun replaceAllStreak(streak: Streak)

    @Query("DELETE FROM streak_table")
    suspend fun deleteAllStreak()
}