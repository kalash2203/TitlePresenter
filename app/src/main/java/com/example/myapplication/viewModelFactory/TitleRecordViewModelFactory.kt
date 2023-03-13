package com.example.myapplication.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.Repository
import com.example.myapplication.viewmodel.TitleRecordViewModel

class TitleRecordViewModelFactory
    (private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
return TitleRecordViewModel(repository) as T
    }
}