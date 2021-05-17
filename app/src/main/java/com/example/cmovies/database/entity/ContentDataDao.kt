package com.example.cmovies.database.entity

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContentDataDao {

    //Overwrite the database entry if the video entry is already present in the database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(content: ContentDataEntity)

    @Query("SELECT * FROM content_data_table")
    fun getAllContent(): LiveData<List<ContentDataEntity>>
}