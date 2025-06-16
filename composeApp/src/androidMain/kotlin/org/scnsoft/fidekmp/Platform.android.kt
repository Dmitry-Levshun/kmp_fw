package org.scnsoft.fidekmp

import android.content.Context
import android.os.Build
import org.scnsoft.fidekmp.shared.config.CustomBuildFields
import java.util.Locale

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val language: String = Locale.getDefault().language
    override val appVersionName: String = CustomBuildFields.APP_VERSION //BuildConfig.VERSION_NAME
}

actual fun getPlatform(): Platform = AndroidPlatform()
