package org.scnsoft.fidekmp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DeliveryInstructionEntity::class, ItemEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deliveryDao(): DeliveryInstructionsDao
}

expect fun getDatabase(): AppDatabase

internal const val dataBaseFileName = "fide_database.db"