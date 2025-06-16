package org.scnsoft.fidekmp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences

interface Platform {
    val name: String
    val language: String
    val appVersionName: String
}

expect fun getPlatform(): Platform
