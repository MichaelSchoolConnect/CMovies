package com.example.cmovies.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cmovies.R
import com.example.cmovies.database.entity.ContentDataEntity
import com.example.cmovies.model.ContentData


class OfflineDataAdapter(private var context: Context, private var list: List<ContentDataEntity>
) : RecyclerView.Adapter<OfflineDataAdapter.OfflineViewHolder>() {

    private val TAG = OfflineDataAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfflineViewHolder {
        Log.i(TAG, "onCreateViewHolder()")
        val view: View = LayoutInflater.from(context).inflate(R.layout.search_results_layout_item_offline,
                parent, false)
        return OfflineViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfflineViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder()")
        val offlineDataEntity: ContentDataEntity = list[position]
        val temp: String = offlineDataEntity.movieTitle
        holder.temp.text = temp
    }

    // return total item from List
    override fun getItemCount(): Int {
        Log.i(TAG, "getItemCount()")
        return list.size
    }

    class OfflineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var temp: TextView = itemView.findViewById(R.id.more_details_title_name_offline)
    }
}