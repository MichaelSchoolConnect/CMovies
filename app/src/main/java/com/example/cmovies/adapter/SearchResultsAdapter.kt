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
import com.example.cmovies.model.ContentData
import com.example.cmovies.R

 class SearchResultsAdapter(private var context: Context, private var data: List<ContentData>
) : RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder>() {

    private val TAG = SearchResultsAdapter::class.java.simpleName

    // Inflate the layout when ViewHolder is created.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
        Log.i(TAG, "Inflating the layout.")
        val view = LayoutInflater.from(context).inflate(R.layout.search_results_layout_item, parent, false)
        return SearchResultsViewHolder(view)
    }

    // return total item from List
    override fun getItemCount(): Int {
        if(data.isEmpty()){
            Log.i(TAG, "List is empty.")
        }else{}
        return data.size
    }

    // Bind data
    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
        Log.i(TAG, "Binding data.")

        val contentViewHolder: SearchResultsViewHolder = holder
        val content = data[position]
        contentViewHolder.movieName.text = content.movieTitle

        //Load image from url
        Glide.with(context)
            .load(content.imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(contentViewHolder.poster)

        //Set the Recyclerview onClick and pass data to an Intent
        holder.itemView.setOnClickListener {
            Log.i(TAG, "Position: " + position + "Movie Name: " + content.movieTitle)
             val intent = Intent(context, MoreDetailsActivity::class.java)
             intent.putExtra("Title", content.movieTitle)
             context.startActivity(intent)
        }
    }

    inner class SearchResultsViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        //Holds the text view
        val movieName : TextView = itemView.findViewById(R.id.more_details_title_name)
        val poster : ImageView = itemView.findViewById(R.id.more_details_poster)
    }
}