package com.example.medsafecycle.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GuestLimbah::class], version = 1)
abstract class GuestLimbahDatabase : RoomDatabase() {

    abstract fun favoriteUserDao(): GuestLimbahDao
    companion object {
        @Volatile
        private var INSTANCE: GuestLimbahDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): GuestLimbahDatabase {
            if (INSTANCE == null) {
                synchronized(GuestLimbahDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        GuestLimbahDatabase::class.java, "guest_limbah_database")
                        .build()
                }
            }
            return INSTANCE as GuestLimbahDatabase
        }
    }

}