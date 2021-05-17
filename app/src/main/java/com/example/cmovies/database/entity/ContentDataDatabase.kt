package com.example.cmovies.database.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ContentDataEntity::class], version = 1, exportSchema = false)
abstract class ContentDataDatabase : RoomDatabase() {

    private val TAG: String = ContentDataDatabase::class.java.simpleName

    companion object {
        @Volatile
        private var INSTANCE: ContentDataDatabase? = null

        fun getInstance(context: Context): ContentDataDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {

                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            ContentDataDatabase::class.java,
                            "content_database"
                    )
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

    abstract fun contentDatabaseDao(): ContentDataDao?
}