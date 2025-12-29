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
        val FIRST_LAUNCH_INSTALL = booleanPreferencesKey("first_launch_install_state")
        val FIRST_LAUNCH_UPDATE = booleanPreferencesKey("first_launch_update_state")
        val LAST_VERSION_NAME = stringPreferencesKey("last_ver_name_state")
    }


    suspend fun updateFirstLaunchState() {
        context.launchDatastore.edit { state ->
            state[LaunchPrefs.FIRST_LAUNCH_INSTALL] = true
        }
    }

    fun readFirstLaunchState() : Flow<Boolean> {
        return context.launchDatastore.data.map { state ->
            state[LaunchPrefs.FIRST_LAUNCH_INSTALL] ?: false
        }
    }

    suspend fun updateUpdateLaunchState() {
        context.launchDatastore.edit { state ->
            state[LaunchPrefs.FIRST_LAUNCH_UPDATE] = true
        }
    }

    fun readUpdateLaunchState() : Flow<Boolean> {
        return context.launchDatastore.data.map { state ->
            state[LaunchPrefs.FIRST_LAUNCH_UPDATE] ?: false
        }
    }

    suspend fun updateLastVersionName(name: String) {
        context.launchDatastore.edit { prefs ->
            prefs[LaunchPrefs.LAST_VERSION_NAME] = name
        }
    }

    fun readLastVersionName() : Flow<String> {
        return context.launchDatastore.data.map { prefs ->
            prefs[LaunchPrefs.LAST_VERSION_NAME] ?: ""
        }
    }
}
