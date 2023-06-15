package com.example.medsafecycle.database

import android.content.Context
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Database(entities = [GuestLimbah::class], version = 1)
@TypeConverters(GuestLimbahTypeConverters::class)
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

class GuestLimbahTypeConverters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<String>): String {
        return Gson().toJson(list)
    }

}