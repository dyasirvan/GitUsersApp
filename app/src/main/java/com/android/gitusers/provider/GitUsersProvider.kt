package com.android.gitusers.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.annotation.Nullable
import com.android.gitusers.db.GitUserDao
import com.android.gitusers.db.GitUserDatabase
import com.android.gitusers.model.ResultItemsSearch
import com.android.gitusers.model.fromContentValues
import com.android.gitusers.utils.Constants.Companion.TABLE_NAME

class GitUsersProvider : ContentProvider() {

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        when(sUriMatcher.match(uri)){
            GIT -> {
                throw IllegalArgumentException("Invalid URI, only can update with ID$uri")

            }
            GIT_TAGS -> {
                throw IllegalArgumentException("Invalid URI, only can update with ID$uri")
            }
            GIT_ID -> {
                val count: Int = gitUserDao.deleteById(Integer.parseInt(uri.lastPathSegment))
                context?.contentResolver?.notifyChange(uri, null)
                return count
            }
            else -> {
                throw IllegalArgumentException("Unknown URI$uri")
            }
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    @Nullable
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (sUriMatcher.match(uri)) {
            GIT -> {
                run {
                    if (context == null) {
                        Log.d(TAG, "insert: null context")
                        return null
                    }
                    val resultItemsSearch: ResultItemsSearch? = values?.let { fromContentValues(it) }
                    val id: Long = gitUserDao.upsertByProvider(resultItemsSearch)
                    Log.d(TAG, "insert: id : " + id + " Movie Id :" + (resultItemsSearch?.id ?: 0))
                    if (id != 0L) {
                        context?.contentResolver?.notifyChange(uri, null)
                        return ContentUris.withAppendedId(uri, id)
                    }
                }
                run { throw java.lang.IllegalArgumentException("Invalid URI cannot insert with ID or Tags : $uri") }
            }
            GIT_ID, GIT_TAGS -> {
                throw java.lang.IllegalArgumentException("Invalid URI cannot insert with ID or Tags : $uri")
            }
            else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")
        }

    }

    override fun onCreate(): Boolean {
        gitUserDao = context?.let { GitUserDatabase.invoke(it).getDatasDao() }!!
        return true
    }

    @Nullable
    override fun query(
            uri: Uri, projection: Array<String>?, selection: String?,
            selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor
        if(context == null){
            return null
        }
        return when (sUriMatcher.match(uri)) {
            GIT -> {
                cursor = gitUserDao.selectAll()!!
                cursor.setNotificationUri(context?.contentResolver, uri)
                cursor
            }
            GIT_ID -> {
                cursor = gitUserDao.selectByIdProvider(Integer.parseInt(uri.lastPathSegment))!!
                cursor.setNotificationUri(context?.contentResolver, uri)
                cursor
            }
            else->{
                throw IllegalArgumentException("Unknown URI:$uri")
            }
        }
    }

    override fun update(
            uri: Uri, values: ContentValues?, selection: String?,
            selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    companion object{
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var gitUserDao: GitUserDao
        private const val GIT = 1
        private const val GIT_ID = 2
        private const val GIT_TAGS = 3
        private const val TAG = "ContentProvider"
        private const val AUTHORITY = "com.android.gitusers"

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, GIT)
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", GIT_ID);
        }
    }

}