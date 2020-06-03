package com.android.recroassignment.source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.recroassignment.model.newslist.Article

@Database(
    entities = [Article::class], version = 1
)
abstract class ArticlesDatabase : RoomDatabase() {
    abstract fun getArticlesDao(): ArticlesDao

    companion object {
        @Volatile
        private var INSTANCE: ArticlesDatabase? = null

        fun getDatabase(context: Context): ArticlesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArticlesDatabase::class.java,
                    "reminder_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }

    }
}