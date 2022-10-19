package com.angrymause.wwa_project.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

private const val DEBOUNCE = 500L

class NetworkStatusTracker(context: Context) {
    private val connectiveManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @OptIn(FlowPreview::class)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    val networkStatus = callbackFlow {
        trySend(NetworkStatus.Unavailable)
        val networkStatusCallback =
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    trySend(NetworkStatus.Available)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    trySend(NetworkStatus.LostConnection)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    trySend(NetworkStatus.Unavailable)
                }
            }
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectiveManager.registerNetworkCallback(request, networkStatusCallback)
        awaitClose {
            connectiveManager.unregisterNetworkCallback(networkStatusCallback)
        }
    }
        .debounce(DEBOUNCE)
        .distinctUntilChanged()

}


sealed class NetworkStatus() {
    object Available : NetworkStatus()
    object Unavailable : NetworkStatus()
    object LostConnection : NetworkStatus()
}