import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import com.codingfeline.buildkonfig.gradle.TargetConfigDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinxSerialization)
    id("com.codingfeline.buildkonfig") version "+"
}

val myServerUrl = '\"' + "https://api.example.com" + '\"'
val isBetaFeatureEnabled = true // Could also come from gradle.properties or command line
val appVersion ='\"' + "1.0.0"+ '\"'
val generateCustomBuildConfigTask = tasks.register("generateCustomBuildConfig") {
    group = "build"
    description = "Generates a Kotlin file with custom build fields for commonMain."

    // Define the output directory for the generated file
    // Place it in a way that it's naturally picked up by commonMain, or explicitly add it.
    val outputDir = layout.buildDirectory.dir("generated/commonMain/kotlin/com/yourcompany/shared/config") // Adjust package
    val outputFile = outputDir.map { it.file("CustomBuildFields.kt") }

    outputs.file(outputFile) // Crucial: Tell Gradle this task produces this file

    doLast {
        val fileContent = "package org.scnsoft.fidekmp.shared.config\nobject CustomBuildFields {\n    const val APP_VERSION = $appVersion\n    const val SERVER_URL = $myServerUrl\n    const val IS_BETA_FEATURE_ENABLED = $isBetaFeatureEnabled\n}\n "
            .trimIndent()

        val targetFile = outputFile.get().asFile
        targetFile.parentFile.mkdirs() // Ensure parent directory exists
        targetFile.writeText(fileContent)
        println("Generated custom build fields at: ${targetFile.absolutePath}")
    }
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm("desktop")

    sourceSets.commonMain.get().kotlin.srcDir(generateCustomBuildConfigTask.map { it.outputs.files.singleFile.parentFile }) // Add parent directory
    tasks.withType<KotlinCompile>().configureEach {
        if (name.lowercase().contains("commonMain")) { // More robust check
            dependsOn(generateCustomBuildConfigTask)
        }
    }
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
//            implementation(libs.koin.android.ext)
//            implementation(libs.androidx.datastore.preferences.core)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.material)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.kotlinx.datetime)

            //Datastore
//            api(libs.androidx.datastore.preferences.core)
//            api(libs.androidx.datastore.core.okio)
            api(libs.datastore.preferences)
            api(libs.datastore)
  //          implementation(libs.okio)

//----
            implementation(libs.navigation.compose)
//            implementation(libs.lifecycle.runtime.compose)
            implementation(libs.material.icons.core)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.auth)

            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            api(libs.koin.core)
//            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.napier.logger)
            implementation(libs.sonner)
            implementation(libs.compose.backhandler)
            api(libs.compose.webview)

            implementation(libs.barcode.generator.qr)
            implementation(libs.barcode.generator.oned)
            implementation(libs.paging.compose.common)
            implementation(libs.lens.logger)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }

    }
}

android {
    namespace = "org.scnsoft.fidekmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.scnsoft.fidekmp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
//        getByName("release") {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    dependencies {
        implementation(libs.androidx.core)
        debugImplementation(compose.uiTooling)
    }
}
/*
dependencies {
    debugImplementation(compose.uiTooling)
}
*/
compose.desktop {
    application {
        mainClass = "org.scnsoft.fidekmp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.scnsoft.fidekmp"
            packageVersion = "1.0.0"
        }
    }
}

buildkonfig {
    packageName = "org.scnsoft.fidekmp"

    // default config is required
    defaultConfigs {
        buildConfigField(STRING, "name", "value")
    }
    // flavor is passed as a first argument of defaultConfigs
    defaultConfigs("dev") {
        buildConfigField(STRING, "name", "devValue")
    }

    targetConfigs {
        create("android") {
            buildConfigField(STRING, "name2", "valueAndroid")
            buildConfigField(STRING, "target", "android")
        }

        create("ios") {
            buildConfigField(STRING, "name", "valueIos")
            buildConfigField(STRING, "target", "ios")

        }
        create("jvm") {
            buildConfigField(STRING, "target", "jvm")
        }
        create("desktop") {
            buildConfigField(STRING, "target", "desktop")
        }
    }
    // flavor is passed as a first argument of targetConfigs
    targetConfigs("dev") {
        create("ios") {
            buildConfigField(STRING, "name", "devValueIos")
        }
    }
}


/*
val appVersion = project.findProperty("app.version") as String

val generatedDir = layout.buildDirectory.dir("generated/appversion")

kotlin.sourceSets.getByName("commonMain").kotlin.srcDir(generatedDir)

tasks.register("generateAppVersion") {
    val outputFile = generatedDir.get().file("AppVersion.kt").asFile
    inputs.property("version", appVersion)
    outputs.file(outputFile)
    doLast {
        outputFile.parentFile.mkdirs()
        outputFile.writeText(
            """
            package generated

            internal const val APP_VERSION = "$appVersion"
            """.trimIndent()
        )
    }
}

tasks.named("compileKotlinMetadata") {
    dependsOn("generateAppVersion")
}
*/


/*
val myServerUrl = "https://api.example.com"
val isBetaFeatureEnabled = true // Could also come from gradle.properties or command line

    val generateCustomBuildConfigTask = tasks.register("generateCustomBuildConfig") {
        group = "build"
        description = "Generates a Kotlin file with custom build fields for commonMain."

        // Define the output directory for the generated file
        // Place it in a way that it's naturally picked up by commonMain, or explicitly add it.
        val outputDir = layout.buildDirectory.dir("generated/commonMain/kotlin/com/yourcompany/shared/config") // Adjust package
        val outputFile = outputDir.map { it.file("CustomBuildFields.kt") }

        outputs.file(outputFile) // Crucial: Tell Gradle this task produces this file

        doLast {
            val fileContent = """
    package com.yourcompany.shared.config // Match the outputDir package

    object CustomBuildFields {
        const val SERVER_URL = "$myServerUrl"
        const val IS_BETA_FEATURE_ENABLED = $isBetaFeatureEnabled
        // val API_KEY: String? = ${if (someApiKeyFromProperties != null) "\"$someApiKeyFromProperties\"" else "null"} // Example with nullable
    }
            """.trimIndent()

            val targetFile = outputFile.get().asFile
            targetFile.parentFile.mkdirs() // Ensure parent directory exists
            targetFile.writeText(fileContent)
            println("Generated custom build fields at: ${targetFile.absolutePath}")
        }
    }

    // Add the generated sources to the commonMain source set
    kotlin {
        sourceSets.commonMain.get().kotlin.srcDir(generateCustomBuildConfigTask.map { it.outputs.files.singleFile.parentFile }) // Add parent directory

        // Ensure the generation task runs before Kotlin compilation for commonMain
        // This is important so the generated file exists when the compiler needs it.
        tasks.withType<KotlinCompile<*>>().configureEach {
            if (name.lowercase().contains("commonmain")) { // More robust check
                dependsOn(generateCustomBuildConfigTask)
            }
        }

 */