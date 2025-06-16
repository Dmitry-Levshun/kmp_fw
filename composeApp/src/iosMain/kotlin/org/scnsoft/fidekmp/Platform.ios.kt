package org.scnsoft.fidekmp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import org.scnsoft.fidekmp.shared.config.CustomBuildFields
import platform.Foundation.NSBundle
import platform.UIKit.UIDevice
import platform.Foundation.NSLocale
import platform.Foundation.NSURL
import platform.Foundation.NSFileManager
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSUserDomainMask
import platform.Foundation.currentLocale
import platform.Foundation.localeIdentifier
import kotlin.text.firstOrNull
import kotlin.text.split

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override val language: String = getCurrentLanguage()
    override val appVersionName: String = CustomBuildFields.APP_VERSION

    //    override val appVersionName: String =
//        "iOS " + NSBundle.mainBundle.infoDictionary?.get("CFBundleVersion")
    private fun getCurrentLanguage(): String {
        // This gets the language code from the current locale identifier
        val localeId = NSLocale.currentLocale.localeIdentifier
        return localeId.split("_").firstOrNull() ?: localeId.split("-").firstOrNull() ?: localeId
    }
}

actual fun getPlatform(): Platform = IOSPlatform()

@OptIn(ExperimentalForeignApi::class)
actual fun createDataStore(): DataStore<Preferences> = createDataStore(
    producePath = {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        requireNotNull(documentDirectory).path + "/$dataStoreFileName"
    }
)