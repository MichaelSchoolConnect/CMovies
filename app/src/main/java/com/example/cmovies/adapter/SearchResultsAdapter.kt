package com.example.cmovies.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.cmovies.pojo.Movies
import com.example.cmovies.R

class SearchResultsAdapter internal constructor(
    context: Context?,
    listItems: LiveData<List<Movies?>?>?
) : RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder>()  {

    private val TAG = SearchResultsAdapter::class.java.simpleName

    private var context: Context? = null
    private var inflater: LayoutInflater? = null;

    private var data: List<Movies> = emptyList<Movies>()



    // Inflate the layout when ViewHolder is created.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
        Log.i(TAG, "Inflating the layout.")
        val view = LayoutInflater.from(context).inflate(R.layout.search_results_layout, parent, false)
        return SearchResultsViewHolder(view)
    }


    // return total item from List
    override fun getItemCount(): Int {
        return data.size
    }

    // Bind data
    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
        Log.i(TAG, "Binding data.")
        // Get houses position of item in recyclerview to bind data and assign values from list
        val housesViewHolder: SearchResultsViewHolder = holder as SearchResultsViewHolder
        val houses = data[position]
        housesViewHolder.movieName.text = houses.movieTitle

        //Get image from url
        /*Glide.with(this@MainActivity)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .into(imageView)*/

        //Set the Recyclerview onClick and pass data to an Intent
        holder.itemView.setOnClickListener {
            Log.i(TAG, "Position: " + position + "Movie Name: " + houses.movieTitle)
            /* val intent = Intent(context, HousesInfoActivity::class.java)
             intent.putExtra("id", houses.id)
             context!!.startActivity(intent)*/
        }
    }

    inner class SearchResultsViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        //Holds the text view
        val movieName : TextView = itemView.findViewById(R.id.houses_info_tv1)
    }
}