package com.example.food2forkmvvm.presentation

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

/**
 * Exposes the App by generating the app's top components
 * Its done by Hilt
 */
@HiltAndroidApp
class BaseApplication : Application(){
    /**
     * To keep track of dark and light theme
     * Should be saved in Data store or cache
     */
    val isDarkTheme = mutableStateOf(false)

    fun toggleTheme(){
        isDarkTheme.value = !isDarkTheme.value
    }
}