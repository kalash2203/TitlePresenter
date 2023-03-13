package com.example.myapplication.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.models.Title

//Room Database class for providing the access point for storing the data locally.
@Database(entities= [Title::class], version = 1, exportSchema = false)
abstract class TitleDatabase:RoomDatabase() {
    abstract fun titleDao(): TitleDao

    companion object {
        private const val DATABASE_NAME="title_database"

        @Volatile
        private var instance: TitleDatabase?=null
        fun getInstance(context: Context): TitleDatabase {
            return instance?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    TitleDatabase::class.java,
                    DATABASE_NAME
                ).build().also { instance=it }
            }
        }
    }
}