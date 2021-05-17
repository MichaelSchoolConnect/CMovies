package com.example.cmovies.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cmovies.database.entity.ContentDataDatabase
import com.example.cmovies.database.entity.ContentDataEntity
import com.example.cmovies.model.ContentData
import com.example.cmovies.repo.MyRepository


class ContentViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG: String = ContentViewModel::class.java.simpleName

    // Online
    private var contentData: LiveData<List<ContentData>>? = null

    // Offline
    private var getAllOfflineData: LiveData<List<ContentDataEntity>>? = null

    /**
     * init{} is called immediately when this ViewModel is created.
     */
    init{
        Log.i(TAG, "init")
        // Get data from the repo
        val repo: MyRepository = MyRepository.getInstance()!!
        contentData = repo.getMutableMoviesLiveData()

        // Get data from the offline database.
        val database: ContentDataDatabase = ContentDataDatabase.getInstance(application)
        getAllOfflineData = database.contentDatabaseDao()?.getAllContent()
    }

    //Gets called when theres internet connection
    fun getContentList(): LiveData<List<ContentData>>? {
        Log.i(TAG, "getContentList")
        return contentData
    }

    //Gets called when there no internet connection. Caching
    fun getDataFromOfflineDB(): LiveData<List<ContentDataEntity>>? {
        Log.i(TAG, "Reading from the DataBase")
        return getAllOfflineData
    }
}