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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cmovies.adapter.OfflineDataAdapter
import com.example.cmovies.adapter.SearchResultsAdapter
import com.example.cmovies.repo.MyRepository
import com.example.cmovies.viewmodel.ContentViewModel


class SearchResultsActivity : AppCompatActivity() {

    //A String constant for Logging.
    private val TAG = SearchResultsActivity::class.java.simpleName

    //Adapter that binds data read  from the MyRepository class.
    private lateinit var mAdapter: SearchResultsAdapter
    private lateinit var contentLoadingProgressBar: ContentLoadingProgressBar
    private lateinit var viewModel: ContentViewModel
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try{
            setContentView(R.layout.activity_search_results)
            Log.i(TAG, "onCreate()")

            //Show LoadingProgressBar
            contentLoadingProgressBar = findViewById(R.id.loadingBar)
            contentLoadingProgressBar.show()

            //initializing the Recyclerview.
            mRecyclerView  = findViewById(R.id.housesRecyclerView)

            //Setting the Recyclerview in a Linear fashion layout via the LayoutManager.
            val layoutManager = LinearLayoutManager(this)
            mRecyclerView.layoutManager = layoutManager
            mRecyclerView.setHasFixedSize(true)

            viewModel = ViewModelProvider(this)[ContentViewModel::class.java]

        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    private fun setUpViewModel() {
        Log.i(TAG, "setUpViewModel()")
        viewModel.getContentList()?.observe(this, { content ->
            content?.let {
                // update UI
                Log.i(TAG, "Update from ViewModel: $content")
                contentLoadingProgressBar.hide()
                mAdapter = SearchResultsAdapter(this, it)
                mRecyclerView.adapter = mAdapter
            }
        })
    }

    private fun setOfflineViewModel() {
        Log.i(TAG, "setOfflineViewModel()")
        /*viewModel.getDataFromOfflineDB()?.observe(this, { content ->
            content?.let {
                contentLoadingProgressBar.hide()
            if(content.isNullOrEmpty()){
                val offlineDataAdapter = OfflineDataAdapter(this, it)
                mRecyclerView.adapter = offlineDataAdapter
            }else{
                val text : TextView = findViewById(R.id.noInternet)
                text.visibility = View.VISIBLE
             }
            }
            Log.i(TAG, content.toString())
        })*/
    }

    // Monitor network  state changes
    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val notConnected = intent.getBooleanExtra(ConnectivityManager
                    .EXTRA_NO_CONNECTIVITY, false)
            if (notConnected) { //Network off.
                //MyRepository.getInstance()
                setOfflineViewModel()
                Toast.makeText(context, "No Data", Toast.LENGTH_LONG).show()
            } else { //Network on.
               MyRepository.getInstance()?.getContent(context)
                setUpViewModel()
            }
        }
    }

    override fun onStart() {
        Log.i(TAG, "onStart()")
        super.onStart()
        registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }
}