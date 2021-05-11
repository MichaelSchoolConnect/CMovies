package com.example.cmovies

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cmovies.adapter.SearchResultsAdapter
import com.example.cmovies.network.NetworkUtils
import com.example.cmovies.repo.MyRepository
import com.example.cmovies.viewmodel.MoviesViewModel

class SearchResultsActivity : AppCompatActivity() {

    //A String constant for Logging.
    private val TAG = SearchResultsActivity::class.java.simpleName

    //The HousesActivity context instead of calling .this all the time where a context is wanted.
    private var context: Context? = null

    //Recyclerview that shows a list of houses.
    private var mRecyclerView: RecyclerView? = null

    //Adapter that binds data read  from the MyRepository class.
    private lateinit var mAdapter: SearchResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        //Show LoadingProgressBar
        val contentLoadingProgressBar: ContentLoadingProgressBar = findViewById(R.id.loadingBar)
        contentLoadingProgressBar.show()

        //initializing the Recyclerview.
        val mRecyclerView: RecyclerView  = findViewById(R.id.housesRecyclerView)

        //Setting the Recyclerview in a Linear fashion layout via the LayoutManager.
        val layoutManager = LinearLayoutManager(applicationContext)
        mRecyclerView.layoutManager = layoutManager

        // Setting up our view model
        val viewModel: MoviesViewModel =
            ViewModelProvider(this).get(MoviesViewModel::class.java)

        // Observe the view model
        viewModel.getHousesLiveData()?.observe(this){
            contentLoadingProgressBar.hide()
            mAdapter = SearchResultsAdapter(this, it)
            mRecyclerView.adapter = mAdapter
            Log.i(TAG, "Update from ViewModel: $it")
        }

        //condition to check whether to request data provided there's an internet connection or not.

        if (!NetworkUtils.isInternetAvailable()) {
            // This will start the off-the-UI-thread work that we want to perform.
            MyRepository.getInstance()!!.getHouses()
        } else {
            //Show AlertDialog to prompt user to get a connection.
            Toast.makeText(context, "No connection", Toast.LENGTH_LONG).show()
        }
    }
}