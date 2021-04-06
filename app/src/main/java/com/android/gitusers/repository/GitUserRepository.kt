package com.android.gitusers.repository

import com.android.gitusers.db.GitUserDatabase
import com.android.gitusers.model.ResultItemsSearch

class GitUserRepository(
    val db: GitUserDatabase
) {
    suspend fun insertToDb(data: ResultItemsSearch) = db.getDatasDao().upsert(data)

    fun getDataFromDb() = db.getDatasDao().getAllData()

    fun getNotLiveDataFromDb() = db.getDatasDao().getAllNotLiveData()

    suspend fun deleteDataFromDb(data: ResultItemsSearch) = db.getDatasDao().deleteData(data)
}