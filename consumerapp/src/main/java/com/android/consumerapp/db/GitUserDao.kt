package com.android.consumerapp.db

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.consumerapp.model.ResultItem
import com.android.consumerapp.utils.Constants.Companion.TABLE_NAME

@Dao
interface GitUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(data: ResultItem): Long

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllData(): LiveData<List<ResultItem>>

    @Delete
    suspend fun deleteData(data: ResultItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertByProvider(resultItemsSearch: ResultItem?): Long

    @Query("SELECT * FROM $TABLE_NAME")
    fun selectAll(): Cursor?

    @Query("SELECT * FROM $TABLE_NAME WHERE id=:id")
    fun selectByIdProvider(id: Int): Cursor?

    @Query("DELETE FROM $TABLE_NAME WHERE id = :id")
    fun deleteById(id: Int): Int

}