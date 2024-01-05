package com.example.food2forkmvvm.interactors.app

import android.util.Log
import com.example.food2forkmvvm.util.Constants.TAG
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import javax.net.SocketFactory
import javax.net.ssl.SSLServerSocketFactory

object DoesNetworkHaveInternet {

    fun execute(socketFactory: SocketFactory): Boolean {
        return try {
            Log.d(TAG, "PINGING GOOGLE")
            val socket = socketFactory.createSocket() //Getting a socket from the specific network
            socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            socket.close()
            Log.d(TAG, "PING SUCCESS")
            true
        } catch (e: IOException) {
            Log.e(TAG, "IOException: ${e}")
            false
        }
    }
}