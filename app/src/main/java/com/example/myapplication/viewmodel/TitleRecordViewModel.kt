package com.example.myapplication.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Title
import com.example.myapplication.repository.Repository
import kotlinx.coroutines.launch

class TitleRecordViewModel (val repo:Repository):ViewModel()
{
    val movieLiveData: LiveData<List<Title>>
    get()=repo.titleRecordResponse
    fun mTitleRecord(context : Context)
    {
        viewModelScope.launch {
            repo.getTitles(context)
        }
    }
}