package com.example.derssrosia.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build

class NetworkChangeReceiver : BroadcastReceiver() {

    object NetworkChangeReceiver{

        fun isNetworkConnected(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val activeNetwork =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

                return when {
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } else {
                val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
                return activeNetwork?.isConnected == true
            }
        }

    }

    override fun onReceive(context: Context, intent: Intent) {

    }
}