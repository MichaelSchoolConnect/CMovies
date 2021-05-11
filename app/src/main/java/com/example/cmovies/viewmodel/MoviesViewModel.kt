package com.example.cmovies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cmovies.pojo.Movies
import com.example.cmovies.repo.MyRepository

class MoviesViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: MyRepository? = MyRepository.getInstance()

    private var mLiveData: MutableLiveData<LiveData<List<Movies?>?>?>? = null

    fun MoviesViewModel(application: Application) {
        // The local live data needs to reference the repository live data
        mLiveData = repo!!.getHousesLiveData()
    }

    fun getHousesLiveData(): MutableLiveData<LiveData<List<Movies?>?>?>? {
        return mLiveData
    }
}