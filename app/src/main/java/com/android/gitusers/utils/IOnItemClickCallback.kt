package com.android.gitusers.utils

import com.android.gitusers.model.ResultItemsSearch

interface IOnItemClickCallback {
    fun onItemClicked(data: ResultItemsSearch)
}