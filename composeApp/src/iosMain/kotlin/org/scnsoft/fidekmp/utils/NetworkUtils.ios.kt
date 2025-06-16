package org.scnsoft.fidekmp.utils

import platform.Foundation.NSURLRequest
import platform.Foundation.NSURL
import platform.Foundation.NSURLSession
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.dataTaskWithRequest
import kotlin.coroutines.resume


actual object NetworkUtils {
    actual fun isNetworkAvailable(): Boolean {
        return runBlocking { isConnected() }
    }

    private suspend fun isConnected(): Boolean {
        return suspendCancellableCoroutine { continuation ->
            val url = NSURL(string = "https://www.google.com")
            val request = NSURLRequest.requestWithURL(url)

            val task = NSURLSession.sharedSession.dataTaskWithRequest(
                request = request,
                completionHandler = { _, _, error ->
                    val connected = error == null
                    continuation.resume(connected)
                }
            )

            task.resume()
        }
    }
}