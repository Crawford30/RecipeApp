package com.example.food2forkmvvm.presentation.util

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject
import javax.inject.Singleton


/**
 * We provide this to dagger and Hilt, ConnectivityManager  //provided to Hilt
 */
@Singleton
class ConnectivityManagerLiveData
@Inject
constructor(
    application: Application,
) {
    private val connectionLiveData = ConnectionLiveData(application)

    // observe this in ui
    val isNetworkAvailable = mutableStateOf(false) //we assume we dont have internet

    //===Register the connection
    fun registerConnectionObserver(lifecycleOwner: LifecycleOwner) {
        connectionLiveData.observe(lifecycleOwner, { isConnected ->
            isConnected?.let { isNetworkAvailable.value = it }
        })
    }

    //=====Remove the connection
    fun unregisterConnectionObserver(lifecycleOwner: LifecycleOwner) {
        connectionLiveData.removeObservers(lifecycleOwner)
    }
}