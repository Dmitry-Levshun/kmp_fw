package org.scnsoft.fidekmp.data.local

import androidx.room.Room
import platform.Foundation.*

actual fun getDatabase(): AppDatabase {
    val documentDir = NSFileManager.defaultManager
        .URLsForDirectory(NSDocumentDirectory, NSUserDomainMask)

    val dbPath = "$documentDir/$dataBaseFileName"

    return Room.databaseBuilder<AppDatabase>(
        name = dbPath
    ).build()
}