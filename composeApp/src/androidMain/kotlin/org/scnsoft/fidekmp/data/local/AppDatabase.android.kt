package org.scnsoft.fidekmp.data.local

import androidx.room.Room
import org.scnsoft.fidekmp.ContextUtils

actual fun getDatabase(): AppDatabase {
    return Room.databaseBuilder(
        ContextUtils.applicationContext!!,
        AppDatabase::class.java,
        dataBaseFileName
    ).build()
}