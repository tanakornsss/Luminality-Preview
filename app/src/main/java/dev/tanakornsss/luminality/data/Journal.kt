package dev.tanakornsss.luminality.data

import kotlinx.serialization.Serializable

@Serializable
data class Journal(
    val journal: Map<String, List<String>> = emptyMap()
)
