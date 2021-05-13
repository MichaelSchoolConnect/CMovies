package com.example.cmovies

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
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
import com.example.cmovies.model.ContentData
import com.example.cmovies.repo.MyRepository
import com.example.cmovies.viewmodel.ContentViewModel


class SearchResultsActivity : AppCompatActivity() {

    //A String constant for Logging.
    private val TAG = SearchResultsActivity::class.java.simpleName

    //Adapter that binds data read  from the MyRepository class.
    private lateinit var mAdapter: SearchResultsAdapter
    lateinit var contentLoadingProgressBar: ContentLoadingProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_search_results)
            Log.i(TAG, "onCreate()")

            //Show LoadingProgressBar
            contentLoadingProgressBar = findViewById(R.id.loadingBar)
            contentLoadingProgressBar.show()

            //initializing the Recyclerview.
            val mRecyclerView: RecyclerView = findViewById<RecyclerView>(R.id.housesRecyclerView)

            //Setting the Recyclerview in a Linear fashion layout via the LayoutManager.
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL;
            mRecyclerView.layoutManager = layoutManager

            // Setting up our view model
            val viewModel: ContentViewModel by viewModels()

            // Create the observer which updates the UI.
            viewModel.getMoviesData()!!.observe(this, Observer<List<ContentData>>{ content ->
            // update UI

            Log.i(TAG, "Update from ViewModel: $content")
            contentLoadingProgressBar.hide()
            mAdapter = SearchResultsAdapter(this, content)
            mRecyclerView.adapter = mAdapter
            mAdapter.notifyDataSetChanged()
            })
    }

    // Monitor network  state changes
    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val notConnected = intent.getBooleanExtra(ConnectivityManager
                    .EXTRA_NO_CONNECTIVITY, false)
            if (notConnected) {
                //Prompt user to get a connection.
                contentLoadingProgressBar.hide()
                val text : TextView = findViewById(R.id.noInternet)
                text.visibility = View.VISIBLE
                Toast.makeText(context, "No connection", Toast.LENGTH_LONG).show()
            } else {
                MyRepository.getInstance()?.getMovies(context)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }
}