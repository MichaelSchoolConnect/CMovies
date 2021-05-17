package com.example.cmovies.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "content_data_table")
data class ContentDataEntity(
        @PrimaryKey
        @ColumnInfo(name = "posters")
        var poster: String,

        @ColumnInfo(name = "titles")
        var movieTitle: String) {
}