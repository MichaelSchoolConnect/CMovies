package com.example.cmovies.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cmovies.pojo.Movies
import com.example.cmovies.repo.MyRepository


class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val movies: MutableLiveData<List<Movies>> by lazy {
        MutableLiveData<List<Movies>>().also {
            performAsyncOp()
        }
    }

    fun getMoviesData(): LiveData<List<Movies>>? {
        Log.i("ViewModel", "getMoviesData")
        return movies
    }

    private fun performAsyncOp() {
        // Do an asynchronous operation to fetch users.
        MyRepository.getInstance()?.getMutableMoviesLiveData()
    }

}