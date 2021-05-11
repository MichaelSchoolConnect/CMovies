package com.example.cmovies.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cmovies.network.NetworkUtils
import com.example.cmovies.pojo.Movies
import com.example.cmovies.thread.AppExecutors
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.net.URL


class MyRepository {
    //URL that points to the houses JSON
    private val HOUSES_ENDPOINT =
        "http://www.omdbapi.com/?t=Mortal+kombat"

    private val mutableHousesLiveData =
        MutableLiveData<LiveData<List<Movies?>?>?>()

    companion object{
        fun getInstance(): MyRepository? {
            var instance: MyRepository? = null
            if (instance == null) {
                synchronized(MyRepository::class.java) {
                    if (instance == null) {
                        instance = MyRepository()
                    }
                }
            }
            return instance
        }
    }

    // This ensures that only the repository can cause a change
    fun getHousesLiveData(): MutableLiveData<LiveData<List<Movies?>?>?> {
        return mutableHousesLiveData
    }

    /**
     * This method gets called from an Activity's onCreate method.
     * It fetches data off the main thread using an Executor(Runnable object)
     */
    fun getHouses() {
        //I made this into a local variable so it can be killed after calling this method to save resources.
        val executors = AppExecutors()
        executors.diskIO()?.execute(Runnable {
            val data: MutableList<Movies> = ArrayList()
            val data1: List<Movies> = ArrayList()
            val url: URL = NetworkUtils.buildUrl(HOUSES_ENDPOINT)!!
            var result: String? = null
            try {
                result = NetworkUtils.getResponseFromHttpUrl(url)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            println("Repo results: $result")
            var jArray: JSONArray? = null
            try {
                jArray = JSONArray(result)
                for (i in 0 until jArray.length()) {

                    //Get objects from the JSONArray.
                    val jsonObject = jArray.getJSONObject(i)

                    //Initialize an object of the class House so we can append data to it.
                    val houseData = Movies("")

                    //Set data to references.
                    houseData.movieTitle = jsonObject.getString("Title")

                    //Store the data into an ArrayList.
                    data.add(houseData)

                    //Post the value(s) of the data to the LiveData Object.
                    mutableHousesLiveData.value
                    Log.i("Repo: ", houseData.toString())
                }
            } catch (j: NullPointerException) {
                j.printStackTrace()
            } catch (j: JSONException) {
                j.printStackTrace()
            }
        })
    }

}