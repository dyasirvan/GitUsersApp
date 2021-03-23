package com.android.consumerapp.utils

import com.android.consumerapp.model.ResultItem

interface IOnItemClickCallback {
    fun onItemClicked(data: ResultItem)
}