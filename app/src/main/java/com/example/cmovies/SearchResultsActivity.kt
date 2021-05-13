package com.example.cmovies

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cmovies.adapter.SearchResultsAdapter
import com.example.cmovies.network.NetworkUtils
import com.example.cmovies.pojo.Movies
import com.example.cmovies.repo.MyRepository
import com.example.cmovies.viewmodel.MoviesViewModel


class SearchResultsActivity : AppCompatActivity() {

    //A String constant for Logging.
    private val TAG = SearchResultsActivity::class.java.simpleName

    //Adapter that binds data read  from the MyRepository class.
    private lateinit var mAdapter: SearchResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_search_results)
            Log.i(TAG, "onCreate()")
            //Show LoadingProgressBar
            val contentLoadingProgressBar: ContentLoadingProgressBar = findViewById(R.id.loadingBar)
            contentLoadingProgressBar.show()

            //initializing the Recyclerview.
            val mRecyclerView: RecyclerView = findViewById<RecyclerView>(R.id.housesRecyclerView)

            //Setting the Recyclerview in a Linear fashion layout via the LayoutManager.
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL;
            mRecyclerView.layoutManager = layoutManager

            // Use the 'by viewModels()' Kotlin property delegate
            // from the activity-ktx artifact
            // Setting up our view model
            val viewModel: MoviesViewModel by viewModels()

        // Create the observer which updates the UI.
        viewModel.getMoviesData()?.observe(this, Observer<List<Movies>>{ content ->
            // update UI
            Log.i(TAG, "Update from ViewModel: $content")
            contentLoadingProgressBar.hide()
            mAdapter = SearchResultsAdapter(this, content)
            mRecyclerView.adapter = mAdapter
            mAdapter.notifyDataSetChanged()
        })
            //condition to check whether to request data provided there's an internet connection or not.
            if (NetworkUtils.isInternetAvailable(this)) {
                // This will start the off-the-UI-thread work that we want to perform.
                MyRepository.getInstance()?.getMovies(this)
            } else {
                //Prompt user to get a connection.
                contentLoadingProgressBar.hide()
                val text : TextView = findViewById(R.id.noInternet)
                text.visibility = View.VISIBLE
                Toast.makeText(this, "No connection", Toast.LENGTH_LONG).show()
            }

    }
}