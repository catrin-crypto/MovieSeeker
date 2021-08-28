package com.example.movieseeker.framework.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.net.ConnectivityManager

import android.net.NetworkInfo

import android.net.wifi.WifiManager

class MainBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            val networkInfo: NetworkInfo? =
                intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO)
            networkInfo?.let {
                if (networkInfo.isConnected) {
                    // Wifi is connected
                    StringBuilder().apply {
                        append("Inetify\n")
                        append("Wifi is connected (We can download Gigabytes!): $networkInfo")
                        toString().also {
                            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        } else if (intent.action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            val networkInfo: NetworkInfo? =
                intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO)
            networkInfo?.let {
                if (networkInfo.type == ConnectivityManager.TYPE_WIFI &&
                    !networkInfo.isConnected
                ) {
                    // Wifi is disconnected
                    StringBuilder().apply {
                        append("Inetify\n")
                        append("Wifi is disconnected (Stop any download and display warning!): $networkInfo")
                        toString().also {
                            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}