package com.example.medsafecycle.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.medsafecycle.HistoryResponseItem

@Database(
    entities = [HistoryResponseItem::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)
abstract class LimbahDatabase : RoomDatabase() {

    abstract fun limbahDao(): LimbahDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: LimbahDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): LimbahDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    LimbahDatabase::class.java, "limbah_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}