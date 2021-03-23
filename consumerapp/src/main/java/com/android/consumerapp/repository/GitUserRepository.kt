package com.android.consumerapp.repository

import com.android.consumerapp.db.GitUserDatabase
import com.android.consumerapp.model.ResultItem

class GitUserRepository(
    val db: GitUserDatabase
) {
    suspend fun insertToDb(data: ResultItem) = db.getDatasDao().upsert(data)

    fun getDataFromDb() = db.getDatasDao().getAllData()

    suspend fun deleteDataFromDb(data: ResultItem) = db.getDatasDao().deleteData(data)
}