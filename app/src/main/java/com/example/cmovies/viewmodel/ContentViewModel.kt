package com.example.cmovies.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cmovies.model.ContentData
import com.example.cmovies.repo.MyRepository


class ContentViewModel(application: Application) : AndroidViewModel(application) {

    private val contentData: MutableLiveData<List<ContentData>> by lazy {
        MutableLiveData<List<ContentData>>().also {
            performAsyncOp()
        }
    }

    fun getMoviesData(): LiveData<List<ContentData>>? {
        Log.i("ViewModel", "getMoviesData")
        return contentData
    }

    private fun performAsyncOp() {
        // Do an asynchronous operation to fetch users.
        MyRepository.getInstance()?.getMutableMoviesLiveData()
    }

}