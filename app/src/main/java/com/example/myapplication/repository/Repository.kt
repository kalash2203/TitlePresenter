package com.example.myapplication.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.models.Title
import com.example.myapplication.network.RetrofitBuilder
import com.example.myapplication.roomDB.TitleDatabase

//repository class to handle the logic of caching the remote server response insite the local database.

class Repository {
    private val _titleRecordResponse=MutableLiveData<List<Title>>()
   val titleRecordResponse: LiveData<List<Title>>
   get()= _titleRecordResponse

  suspend  fun getTitles(context : Context)
    {
        getTitleRecord(context)
        val title = TitleDatabase.getInstance(context).titleDao().getTitle()
        _titleRecordResponse.postValue(title)
    }

    private suspend fun getTitleRecord(context: Context)
    {
        val title = TitleDatabase.getInstance(context).titleDao().getTitle()
        if (title.isEmpty()) {
            val result = RetrofitBuilder.getRetrofitBuilder().getApi().TitleRecord()
            if (result.isSuccessful && result.body() != null) {
                TitleDatabase.getInstance(context).titleDao().insertTitle(result.body()!!)

            }
        }else{
            return
        }
    }
}