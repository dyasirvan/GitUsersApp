package com.android.consumerapp.helper

import android.database.Cursor
import com.android.consumerapp.model.ResultItem

object MappingHelper {

    fun mapCursorToArrayList(resultItemCursor: Cursor?): ArrayList<ResultItem> {
        val resultItemList = ArrayList<ResultItem>()

        resultItemCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val name = getString(getColumnIndexOrThrow("login"))
                val photo = getString(getColumnIndexOrThrow("avatar_url"))
                resultItemList.add(ResultItem(login = name, avatar_url = photo, id = id))
            }
        }
        return resultItemList
    }

    fun mapCursorToObject(resultItemCursor: Cursor?): ResultItem {
        var resultItem = ResultItem()
        resultItemCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow("id"))
            val name = getString(getColumnIndexOrThrow("login"))
            val photo = getString(getColumnIndexOrThrow("avatar_url"))
            resultItem = ResultItem(login = name, avatar_url = photo, id = id)
        }
        return resultItem
    }
}