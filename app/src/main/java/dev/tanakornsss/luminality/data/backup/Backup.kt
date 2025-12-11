package dev.tanakornsss.luminality.data.backup

import dev.tanakornsss.luminality.data.journal.Journal
import dev.tanakornsss.luminality.data.streak.Streak

data class Backup(
    val journal: List<Journal>,
    val streak: Streak
)
