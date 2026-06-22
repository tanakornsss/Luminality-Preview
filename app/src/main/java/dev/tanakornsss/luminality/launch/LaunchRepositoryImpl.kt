package dev.tanakornsss.luminality.launch

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dev.tanakornsss.luminality.BuildConfig
import kotlinx.coroutines.flow.first

private val Context.launchDatastore by preferencesDataStore(name = "launch_prefs")

class LaunchRepositoryImpl(private val context: Context) : LaunchRepository {

    // There will be a tooltip whenever the app updates or the app was newly installed

    object LaunchPrefs {
        val VERSION_KEY = intPreferencesKey("installed_version")
    }

    override suspend fun getLaunchTypeOnce(): LaunchType {
        val prefs = context.launchDatastore.data.first()
        val saved = prefs[LaunchPrefs.VERSION_KEY]
        val current = BuildConfig.VERSION_CODE

        return when {
            (saved == null) -> LaunchType.FIRST_INSTALL
            (saved < current) -> LaunchType.FIRST_AFTER_UPDATE
            else -> LaunchType.NORMAL
        }
    }

    override suspend fun markLaunched() {
        context.launchDatastore.edit { prefs ->
            prefs[LaunchPrefs.VERSION_KEY] = BuildConfig.VERSION_CODE
        }
    }
}
