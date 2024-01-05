package com.example.food2forkmvvm.dataStore

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.food2forkmvvm.presentation.BaseApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SettingsDataStore
@Inject constructor(private val app: BaseApplication) {

    companion object {
        private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme_key")
    }


    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val scope = CoroutineScope(Main)

    val isDarkTheme = mutableStateOf(false)

    init {
        observeDataStore()
    }

    //To toggle the theme
    fun toggleTheme() {
        scope.launch {
            app.dataStore.edit { preferences ->
                val current = preferences[DARK_THEME_KEY] ?: false
                preferences[DARK_THEME_KEY] = !current //Switching the value to the opposite each time
            }
        }
    }

    private fun observeDataStore() {
        app.dataStore.data.onEach { preferences ->
            preferences[DARK_THEME_KEY]?.let { isDark: Boolean ->
                isDarkTheme.value = isDark
            }
        }.launchIn(scope)
    }
}

//@Singleton
//class SettingsDataStore
//@Inject
//constructor(context: Context) {
//    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
////    private val Context.dataStore: DataStore<Preferences> =  DataStore<Preferences> by preferencesDataStore("Settings")
//
// private val scope = CoroutineScope(Main)
//
//    init {
//        observeDataStore()
//    }
//
//
//
//
//
//
//
//
//}