package com.surajappdeveloper.ruptokassignment.helper

import android.content.Context
import android.net.ConnectivityManager

class NetworkHelper {

    companion object {

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo?.isConnected==true
        }

    }
}