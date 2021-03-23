package com.android.gitusers.model

import android.content.ContentValues
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.android.gitusers.utils.Constants
import com.android.gitusers.utils.Constants.Companion.TABLE_NAME
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = TABLE_NAME)
data class ResultItemsSearch(
        var avatar_url: String? = "",
        var events_url: String? = "",
        var followers_url: String? = "",
        var following_url: String? = "",
        var gists_url: String? = "",
        var gravatar_id: String? = "",
        var html_url: String? = "",
        @PrimaryKey
        @NonNull
        var id: Int? = 0,
        var login: String? = "",
        var node_id: String? = "",
        var organizations_url: String? = "",
        var received_events_url: String? = "",
        var repos_url: String? = "",
        var score: Int? = 0,
        var site_admin: Boolean? = false,
        var starred_url: String? = "",
        var subscriptions_url: String? = "",
        var type: String? = "",
        var url: String? = ""
): Parcelable

@Ignore
fun ResultItemsSearch(id: Int): ResultItemsSearch {
    val ris = ResultItemsSearch()
    ris.id = id
    return ris
}

@Ignore
fun fromContentValues(values: ContentValues): ResultItemsSearch? {
    val resultItemsSearch = ResultItemsSearch(-1)
    if (values.containsKey(Constants._ID)) resultItemsSearch.id = values.getAsInteger(Constants._ID)
    if (values.containsKey(Constants.AVATAR_URL)) resultItemsSearch.avatar_url = values.getAsString(Constants.AVATAR_URL)
    if (values.containsKey(Constants.EVENTS_URL)) resultItemsSearch.events_url = values.getAsString(Constants.EVENTS_URL)
    if (values.containsKey(Constants.FOLLOWER_URL)) resultItemsSearch.followers_url = values.getAsString(Constants.FOLLOWER_URL)
    if (values.containsKey(Constants.FOLLOWING_URL)) resultItemsSearch.following_url = values.getAsString(Constants.FOLLOWING_URL)
    if (values.containsKey(Constants.GISTS_URL)) resultItemsSearch.gists_url = values.getAsString(Constants.GISTS_URL)
    if (values.containsKey(Constants.GRAVATAR_URL)) resultItemsSearch.gravatar_id = values.getAsString(Constants.GRAVATAR_URL)
    if (values.containsKey(Constants.HTML_URL)) resultItemsSearch.html_url = values.getAsString(Constants.HTML_URL)
    if (values.containsKey(Constants.LOGIN)) resultItemsSearch.login = values.getAsString(Constants.LOGIN)
    if (values.containsKey(Constants.NOTE_ID)) resultItemsSearch.node_id = values.getAsString(Constants.NOTE_ID)
    if (values.containsKey(Constants.ORGANIZATIONS_URL)) resultItemsSearch.organizations_url = values.getAsString(Constants.ORGANIZATIONS_URL)
    if (values.containsKey(Constants.RECEIVED_EVENTS_URL)) resultItemsSearch.received_events_url = values.getAsString(Constants.RECEIVED_EVENTS_URL)
    if (values.containsKey(Constants.REPOS_URL)) resultItemsSearch.repos_url = values.getAsString(Constants.REPOS_URL)
    if (values.containsKey(Constants.SCORE)) resultItemsSearch.score = values.getAsInteger(Constants.SCORE)
    if (values.containsKey(Constants.SITES_ADMIN)) resultItemsSearch.site_admin = values.getAsBoolean(Constants.SITES_ADMIN)
    if (values.containsKey(Constants.STARRED_URL)) resultItemsSearch.starred_url = values.getAsString(Constants.STARRED_URL)
    if (values.containsKey(Constants.SUBSCRIPTIONS_URL)) resultItemsSearch.subscriptions_url = values.getAsString(Constants.SUBSCRIPTIONS_URL)
    if (values.containsKey(Constants.TYPE)) resultItemsSearch.type = values.getAsString(Constants.TYPE)
    if (values.containsKey(Constants.URL)) resultItemsSearch.url = values.getAsString(Constants.URL)
    return resultItemsSearch
}



