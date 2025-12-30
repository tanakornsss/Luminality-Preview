package dev.tanakornsss.luminality.launch

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.launchDatastore by preferencesDataStore(name = "launch_prefs")

class LaunchRepository(private val context: Context) {

    // There will be a tooltip whenever the app updates or the app was newly installed

    object LaunchPrefs {
        // True when IS FIRST LAUNCH. Otherwise False.
        val IS_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")
        // True when IS FIRST LAUNCH AFTER UPDATE. Otherwise False.
        val IS_FIRST_LAUNCH_AFTER_UPDATE = booleanPreferencesKey("is_first_launch_after_update")
        // To compare current app version from DataStore with the new one from build config.
        val LAST_LAUNCHED_VERSION = intPreferencesKey("last_launched_version")
    }


    suspend fun markFirstLaunchHandled() {
        context.launchDatastore.edit { state ->
            state[LaunchPrefs.IS_FIRST_LAUNCH] = false
        }
    }

    fun readFirstLaunchState() : Flow<Boolean> {
        return context.launchDatastore.data.map { state ->
            state[LaunchPrefs.IS_FIRST_LAUNCH] ?: true
        }
    }

    suspend fun markUpdateLaunchHandled() {
        context.launchDatastore.edit { state ->
            state[LaunchPrefs.IS_FIRST_LAUNCH_AFTER_UPDATE] = false
        }
    }

    fun readUpdateLaunchState() : Flow<Boolean> {
        return context.launchDatastore.data.map { state ->
            state[LaunchPrefs.IS_FIRST_LAUNCH_AFTER_UPDATE] ?: true
        }
    }

    suspend fun updateLastVersionName(ver: Int) {
        context.launchDatastore.edit { prefs ->
            prefs[LaunchPrefs.LAST_LAUNCHED_VERSION] = ver
        }
    }

    fun readLastVersionCode() : Flow<Int> {
        return context.launchDatastore.data.map { prefs ->
            prefs[LaunchPrefs.LAST_LAUNCHED_VERSION] ?: 0
        }
    }
}
