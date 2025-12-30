package dev.tanakornsss.luminality.launch

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dev.tanakornsss.luminality.BuildConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.launchDatastore by preferencesDataStore(name = "launch_prefs")

class LaunchRepository(private val context: Context) {

    // There will be a tooltip whenever the app updates or the app was newly installed

    object LaunchPrefs {
        val VERSION_KEY = intPreferencesKey("installed_version")
    }

    val launchType: Flow<LaunchType> =
        context.launchDatastore.data.map { prefs ->
            val saved = prefs[LaunchPrefs.VERSION_KEY]
            val current = BuildConfig.VERSION_CODE

            when {
                (saved == null) -> LaunchType.FIRST_INSTALL
                (saved < current) -> LaunchType.FIRST_AFTER_UPDATE
                else -> LaunchType.NORMAL
            }
        }

    suspend fun markLaunched() {
        context.launchDatastore.edit { prefs ->
            prefs[LaunchPrefs.VERSION_KEY] = BuildConfig.VERSION_CODE
        }
    }
}
