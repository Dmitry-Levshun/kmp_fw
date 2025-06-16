package org.scnsoft.fidekmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.scnsoft.fidekmp.di.initKoin
import org.scnsoft.fidekmp.ui.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "fidekmp",
    ) {
        initKoin()
        App()
    }
}
