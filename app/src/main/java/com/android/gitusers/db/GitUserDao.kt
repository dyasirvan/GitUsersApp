package com.android.gitusers.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.gitusers.model.ResultItemsSearch
import com.android.gitusers.utils.Constants.Companion.TABLE_NAME

@Dao
interface GitUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(data: ResultItemsSearch): Long

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllData(): LiveData<List<ResultItemsSearch>>

    @Delete
    suspend fun deleteData(data: ResultItemsSearch)
}