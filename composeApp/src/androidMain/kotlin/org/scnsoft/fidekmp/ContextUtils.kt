package org.scnsoft.fidekmp

import android.content.Context

object ContextUtils {

    var applicationContext: Context? = null

    fun setContext(context: Context) {
        applicationContext = context.applicationContext
    }
}