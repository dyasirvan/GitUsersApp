package com.android.consumerapp.utils

import com.android.consumerapp.model.ResultItem

interface ILoadCallbackGitUsers {
    fun preExecute()

    fun postExecute(resultItem: ArrayList<ResultItem>)
}