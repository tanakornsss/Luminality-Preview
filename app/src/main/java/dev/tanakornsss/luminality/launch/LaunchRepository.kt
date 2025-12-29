package dev.tanakornsss.luminality.launch

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.launchDatastore by preferencesDataStore(name = "launch_prefs")

class LaunchRepository(private val context: Context) {

    // There will be a tooltip whenever the app updates or the app was newly installed

    object LaunchPrefs {
        val IS_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")
        val IS_FIRST_LAUNCH_AFTER_UPDATE = booleanPreferencesKey("is_first_launch_after_update")
        val LAST_LAUNCHED_VERSION = stringPreferencesKey("last_launched_version")
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

    suspend fun updateLastVersionName(name: String) {
        context.launchDatastore.edit { prefs ->
            prefs[LaunchPrefs.LAST_LAUNCHED_VERSION] = name
        }
    }

    fun readLastVersionName() : Flow<String> {
        return context.launchDatastore.data.map { prefs ->
            prefs[LaunchPrefs.LAST_LAUNCHED_VERSION] ?: ""
        }
    }
}
