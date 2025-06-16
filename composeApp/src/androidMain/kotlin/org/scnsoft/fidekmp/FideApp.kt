package org.scnsoft.fidekmp

import android.app.Application
import org.scnsoft.fidekmp.di.initKoin

class FideApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ContextUtils.setContext(this@FideApp)
        initKoin()
    }
}
