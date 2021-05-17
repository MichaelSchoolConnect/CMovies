package com.example.cmovies.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cmovies.MoreDetailsActivity
import com.example.cmovies.R
import com.example.cmovies.database.entity.ContentDataDatabase
import com.example.cmovies.database.entity.ContentDataEntity
import com.example.cmovies.model.ContentData
import com.example.cmovies.thread.AppExecutors




class SearchResultsAdapter(private var context: Context, private var list: List<ContentData>
) : RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder>() {

    private val TAG = SearchResultsAdapter::class.java.simpleName

    // Member variable for the Database
    private var mDb: ContentDataDatabase? = null

     init {
         mDb = ContentDataDatabase.getInstance(context);
     }

    // Inflate the layout when ViewHolder is created.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
        Log.i(TAG, "Inflating the layout.")
        val view = LayoutInflater.from(context).inflate(R.layout.search_results_layout_item, parent, false)
        return SearchResultsViewHolder(view)
    }

    private fun insertOfflineData(poster: String, title: String) {
        val offlineDataEntity = ContentDataEntity(poster, title)
        val executors = AppExecutors()
        executors.executorService.execute {
            Log.i(TAG, "data inserted in offline db")
            mDb?.contentDatabaseDao()?.insert(offlineDataEntity)
        }
    }

    // return total item from List
    override fun getItemCount(): Int {
        return list.size
    }

    // Bind data
    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
        Log.i(TAG, "Binding data.")

        val content = list[position]
        holder.movieTitle.text = content.movieTitle

        //Load image from url
        Glide.with(context)
            .load(content.poster)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.poster)

        //Set the Recyclerview onClick and pass data to an Intent
        holder.itemView.setOnClickListener {
            Log.i(TAG, "Position: " + position + "Movie Name: " + content.movieTitle)
             val intent = Intent(context, MoreDetailsActivity::class.java)
             intent.putExtra("Title", content.movieTitle)
             context.startActivity(intent)
        }

        insertOfflineData(content.poster, content.movieTitle)
    }

     class SearchResultsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //Holds the views
        val movieTitle : TextView = itemView.findViewById(R.id.more_details_title_name_offline)
        val poster : ImageView = itemView.findViewById(R.id.more_details_poster_offline)
    }
}