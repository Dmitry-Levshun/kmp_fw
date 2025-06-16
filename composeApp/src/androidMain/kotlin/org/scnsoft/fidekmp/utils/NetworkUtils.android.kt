package org.scnsoft.fidekmp.utils

import android.content.Context
import android.net.ConnectivityManager
import org.scnsoft.fidekmp.ContextUtils

actual object NetworkUtils {
    actual fun isNetworkAvailable(): Boolean {
        val context = ContextUtils.applicationContext ?: return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}