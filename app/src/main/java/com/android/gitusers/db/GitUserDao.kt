package com.android.gitusers.db

import android.database.Cursor
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertByProvider(resultItemsSearch: ResultItemsSearch?): Long

    @Query("SELECT * FROM $TABLE_NAME")
    fun selectAll(): Cursor?

    @Query("SELECT * FROM $TABLE_NAME WHERE id=:id")
    fun selectByIdProvider(id: Int): Cursor?

    @Query("DELETE FROM $TABLE_NAME WHERE id = :id")
    fun deleteById(id: Int): Int

}