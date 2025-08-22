package org.scnsoft.fidekmp.data.local
import androidx.room.Room
import java.io.File

actual fun getDatabase(): AppDatabase {
    val dbFile = File(System.getProperty("user.home"), dataBaseFileName)
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
    ).build()
}