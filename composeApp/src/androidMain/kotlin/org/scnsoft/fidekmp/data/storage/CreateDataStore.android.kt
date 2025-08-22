package org.scnsoft.fidekmp.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences

import kotlinx.coroutines.runBlocking
import okio.Path.Companion.toPath
import org.scnsoft.fidekmp.ContextUtils
import org.scnsoft.fidekmp.data.storage.dataStoreFileName
/*
actual fun createDataStore(): DataStore<Preferences> {
    return runBlocking {
        getDataStore(producePath = {
            ContextUtils.applicationContext!!.filesDir.resolve(
                dataStoreFileName
            ).absolutePath
        })
    }
}
*/
actual fun createDataStore(): DataStore<Preferences> {
//    return createDataStore {
//        ContextUtils.applicationContext!!.filesDir.resolve(dataStoreFileName).absolutePath
//    }
    return PreferenceDataStoreFactory.createWithPath {
        ContextUtils.applicationContext!!.filesDir.resolve(dataStoreFileName).absolutePath.toPath()
    }
}