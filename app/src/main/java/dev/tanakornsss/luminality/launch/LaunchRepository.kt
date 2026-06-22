package dev.tanakornsss.luminality.launch

interface LaunchRepository {
    suspend fun getLaunchTypeOnce(): LaunchType

    suspend fun markLaunched()

}