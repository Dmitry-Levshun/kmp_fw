package org.scnsoft.fidekmp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import fidekmp.composeapp.generated.resources.Res
import fidekmp.composeapp.generated.resources.compose_multiplatform
import org.scnsoft.fidekmp.Greeting
import org.scnsoft.fidekmp.getPlatform
import org.scnsoft.fidekmp.ui.login.NavGraph
import io.github.farhazulmullick.lenslogger.ui.LensApp

@Composable
fun App() {
    LensApp(
        modifier = Modifier.fillMaxSize(),
// by default enabled, set to false to disable.
        showLensFAB = true
    ) {
        // Your app content goes here
        MainApp()
    }
}
//-------

@Composable
@Preview
fun MainApp() {
    NavGraph()
    /*
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        val platform by remember { mutableStateOf(getPlatform()) }
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                    Text(platform.name)
                    Text(platform.language)
                    Text(platform.appVersionName)
                }
            }
        }
    }
     */
}