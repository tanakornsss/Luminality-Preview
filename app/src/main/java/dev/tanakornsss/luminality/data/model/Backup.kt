package dev.tanakornsss.luminality.data.model

import dev.tanakornsss.luminality.data.streak.Streak

data class Backup(
    val journal: List<Journal>,
    val streak: Streak
)