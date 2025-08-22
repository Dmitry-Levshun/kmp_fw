package org.scnsoft.fidekmp.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual class HttpClientEngineFactory actual constructor() {
    actual fun getHttpEngine(): HttpClientEngine {
        return Darwin.create()
    }
}