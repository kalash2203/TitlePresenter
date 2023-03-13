package com.example.myapplication.roomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.models.Title

//DataAccessObject for performing insertion and selection operation.
@Dao
interface TitleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTitle(title: List<Title>)

    @Query("SELECT * FROM titles")
    suspend fun getTitle(): List<Title>

}