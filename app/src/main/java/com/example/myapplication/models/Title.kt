package com.example.myapplication.models

import androidx.room.Entity
import androidx.room.PrimaryKey

//model class for storing the api Response inside titles table.
@Entity(tableName = "titles")
data class Title(
     var userId : Int?,
     @PrimaryKey
     var id     : Int?,
     var title  : String?,
     var body   : String?
)