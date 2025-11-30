package dev.tanakornsss.luminality.data.old

import kotlinx.serialization.Serializable

@Serializable
data class Journal(
    val entries: Map<String, List<String>> = emptyMap()
)
