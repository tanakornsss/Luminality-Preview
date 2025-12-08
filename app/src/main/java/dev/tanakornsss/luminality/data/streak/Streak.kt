package dev.tanakornsss.luminality.data.streak

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "streak_table")
data class Streak(
    @PrimaryKey val id: Int = 1,
    val currentStreak: Int,
    val lastUsedDate: Long
)
