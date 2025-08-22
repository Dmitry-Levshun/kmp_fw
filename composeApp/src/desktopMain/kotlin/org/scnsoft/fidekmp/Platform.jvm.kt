package org.scnsoft.fidekmp

//import org.scnsoft.fidekmp.shared.config.CustomBuildFields
import java.util.Locale


class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
    override val language: String = Locale.getDefault().language
    override val appVersionName: String = "1.0"//CustomBuildFields.APP_VERSION
}

actual fun getPlatform(): Platform = JVMPlatform()
