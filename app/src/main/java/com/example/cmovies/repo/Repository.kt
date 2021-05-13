package com.example.cmovies.repo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.example.cmovies.network.NetworkUtils
import com.example.cmovies.model.ContentData
import com.example.cmovies.thread.AppExecutors
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.URL


class MyRepository {

    private val mutableContentDataLiveData: MutableLiveData<List<ContentData>> =
            MutableLiveData<List<ContentData>>()

    companion object{
        fun getInstance(): MyRepository? {
            Log.i("Repo class: ", "getInstance() called.")
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

    private fun appendUrl(context: Context): String {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val searchString : String = sharedPreferences.getString("urlString", "").toString()
        return "http://www.omdbapi.com/?s=${searchString}&apikey=3dfd2fcb&t"
    }


    /**
     * This method gets called from an Activity's onCreate method.
     * It fetches data off the main thread using an Executor(Runnable object)
     */
    fun getMovies(context: Context) {
        //I made this into a local variable so it can be killed after calling this method to save resources.
        val searchList = appendUrl(context)
        val executors = AppExecutors()
        executors.executorService.execute {
            Log.i("Repo class: ", "getMovies() function is called.")
            val data: MutableList<ContentData> = ArrayList()
            val url: URL = NetworkUtils.buildUrl(searchList)!!
            var result: String? = null

            try {
                result = NetworkUtils.getResponseFromHttpUrl(url)
                //Log.i("Repo results: ", "$result")
            } catch (e: IOException) {
                Log.i("Repo network: ", e.printStackTrace().toString())
                e.printStackTrace()
            }

            try {
                val jsonObject = JSONObject(result.toString())
                val jsonArray = jsonObject.getJSONArray("Search")
                for(i in 0 until jsonArray.length()){

                    //Get objects from the JSONArray.
                    val item = jsonArray.getJSONObject(i)
                    val movieData = ContentData(item.getString("Poster"),item.getString("Title"))

                    //Add the data into an ArrayList.
                    data.add(movieData)

                    //Update the LiveData with content
                    mutableContentDataLiveData.postValue(data)

                    Log.i("Movie title: ", movieData.movieTitle)
                    Log.i("Movie poster: ", movieData.imageUrl)
                }
                    Log.i(
                        "Repo class: ",
                        "mutuableMoviesLiveData has " + mutableContentDataLiveData.value?.size.toString() + " items."
                    )

            } catch (n: NullPointerException) {
                n.printStackTrace()
                Log.i("Repo class: ", n.printStackTrace().toString())
            } catch (j: JSONException) {
                j.printStackTrace()
                Log.i("Repo class: ", j.printStackTrace().toString())
            }
        }

    }

    // This ensures that only the repository can cause a change
    fun getMutableMoviesLiveData(): LiveData<List<ContentData>> {
        Log.i("Repo class", "getMoviesLiveData() called." + mutableContentDataLiveData.value.toString())
        return mutableContentDataLiveData
    }
}
