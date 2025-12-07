package dev.tanakornsss.luminality.data.streak

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StreakDao {
    @Query("SELECT * FROM streak_table WHERE id = 1")
    fun getStreakFlow(): Flow<Streak?>

    @Query("SELECT * FROM streak_table WHERE id = 1")
    suspend fun getStreakOnce(): Streak?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(streak: Streak)
}