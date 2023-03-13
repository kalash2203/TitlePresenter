package com.example.myapplication.network

import com.example.myapplication.models.Title
import retrofit2.Response
import retrofit2.http.GET
//APIs Interface for network calling using endpoints and method.
interface APIs {
@GET("/posts")
suspend fun TitleRecord():Response<List<Title>>
}