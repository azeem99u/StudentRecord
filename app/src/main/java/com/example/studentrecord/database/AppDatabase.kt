package com.example.studentrecord.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.studentrecord.database.dao.ItemEntityDao
import com.example.studentrecord.database.entity.ItemEntity

@Database(
    entities = [ItemEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var appDatabase: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (appDatabase != null) {
                return appDatabase!!
            }
            appDatabase = Room
                .databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database")
                .build()
            return appDatabase!!
        }
    }
    abstract fun itemEntityDao(): ItemEntityDao
}