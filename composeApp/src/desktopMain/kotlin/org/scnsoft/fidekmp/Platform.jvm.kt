package org.scnsoft.fidekmp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import org.scnsoft.fidekmp.shared.config.CustomBuildFields
import java.util.Locale


class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
    override val language: String = Locale.getDefault().language
    override val appVersionName: String = CustomBuildFields.APP_VERSION
}

actual fun getPlatform(): Platform = JVMPlatform()
