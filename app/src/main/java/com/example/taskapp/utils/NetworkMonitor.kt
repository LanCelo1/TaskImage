package com.example.taskapp.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.MutableLiveData

class NetworkMonitor constructor(private val application: Application) {
    private var isFirst = true
    fun startNetworkCallback() {
        val cm: ConnectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder: NetworkRequest.Builder = NetworkRequest.Builder()

        if (isFirst) {
            onNetworkStateChanged.postValue(isConnectedOrConnecting(application))
            isFirst = false
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(networkCallback)
        } else {
            cm.registerNetworkCallback(
                builder.build(), networkCallback
            )
        }
    }

    fun stopNetworkCallback() {
        val cm: ConnectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(ConnectivityManager.NetworkCallback())
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            onNetworkStateChanged.postValue(true)
        }

        override fun onLost(network: Network) {
            onNetworkStateChanged.postValue(false)
        }
    }

    interface OnNetworkStateChangeListener {
        fun onChangeListener(state: Boolean)
    }

    companion object {
        val onNetworkStateChanged = MutableLiveData<Boolean>()
    }

    private fun isConnectedOrConnecting(context: Context): Boolean {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

}