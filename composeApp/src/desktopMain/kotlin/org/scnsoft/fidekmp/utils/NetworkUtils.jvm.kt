package org.scnsoft.fidekmp.utils

import java.io.IOException
import java.net.InetAddress


actual object NetworkUtils {
    actual fun isNetworkAvailable(): Boolean {
        try {
            val addr = InetAddress.getByName("google.com")
            return true
        } catch (e: IOException) {
            return false
        }
    }
}