package com.example.todaynews.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ArticleEntity::class, FavoriteEntity::class], version = 3)
abstract class ArticleDatabase: RoomDatabase() {
    abstract fun dao(): ArticleDao
    abstract fun favoriteDao(): FavoriteDao

    companion object{
        private var INSTANCE : ArticleDatabase? = null

        fun getDatabase(context: Context): ArticleDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArticleDatabase::class.java,
                    "favorite_articles_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
/*
            fun getDatabase(context: Context): SongsDatabase {
            INSTANCE?.let {
                return it
            }
            INSTANCE = Room.databaseBuilder(
                context,
                SongsDatabase::class.java,
                "songs_database"
            ).build()
            return INSTANCE!!
 */