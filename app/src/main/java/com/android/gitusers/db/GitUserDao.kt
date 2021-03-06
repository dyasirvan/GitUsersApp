package com.android.gitusers.db

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.gitusers.model.ResultItemsSearch
import com.android.gitusers.utils.Constants.Companion.TABLE_NAME
import com.android.gitusers.utils.Constants.Companion._ID

@Dao
interface GitUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(data: ResultItemsSearch): Long

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllData(): LiveData<List<ResultItemsSearch>>

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllNotLiveData(): List<ResultItemsSearch>

    @Delete
    suspend fun deleteData(data: ResultItemsSearch)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertByProvider(resultItemsSearch: ResultItemsSearch?): Long

    @Query("SELECT * FROM $TABLE_NAME")
    fun selectAll(): Cursor?

    @Query("SELECT * FROM $TABLE_NAME WHERE $_ID=:id")
    fun selectByIdProvider(id: Int): Cursor?

    @Query("DELETE FROM $TABLE_NAME WHERE $_ID = :id")
    fun deleteById(id: Int): Int

}