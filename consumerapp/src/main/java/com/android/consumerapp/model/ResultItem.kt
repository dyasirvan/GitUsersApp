package com.android.consumerapp.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.consumerapp.utils.Constants.Companion.TABLE_NAME
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME)
data class ResultItem(
        var avatar_url: String? = null,
        var events_url: String? = null,
        var followers_url: String? = null,
        var following_url: String? = null,
        var gists_url: String? = null,
        var gravatar_id: String? = null,
        var html_url: String? = null,
        @PrimaryKey
        @NonNull
        var id: Int? = 0,
        var login: String? = null,
        var node_id: String? = null,
        var organizations_url: String? = null,
        var received_events_url: String? = null,
        var repos_url: String? = null,
        var score: Int? = 0,
        var site_admin: Boolean? = false,
        var starred_url: String? = null,
        var subscriptions_url: String? = null,
        var type: String? = null,
        var url: String? = null
): Parcelable




