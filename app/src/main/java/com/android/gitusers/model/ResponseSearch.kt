package com.android.gitusers.model

data class ResponseSearch(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<ResultItemsSearch>
)
