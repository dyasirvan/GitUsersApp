package com.android.consumerapp.api

import com.android.consumerapp.model.DetailUser
import com.android.consumerapp.model.Followers
import com.android.consumerapp.model.Following
import com.android.consumerapp.utils.Constants.Companion.TOKEN_GITHUB
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GithubApi {

    @GET("/users/{username}")
    suspend fun detailAccount(
        @Path("username") username: String,
        @Header("Authorization") token: String = TOKEN_GITHUB
    ): Response<DetailUser>

    @GET("/users/{username}/followers")
    suspend fun followerAccount(
        @Path("username") username: String,
        @Header("Authorization") token: String = TOKEN_GITHUB
    ): Response<Followers>

    @GET("/users/{username}/following")
    suspend fun followingAccount(
            @Path("username") username: String,
            @Header("Authorization") token: String = TOKEN_GITHUB
    ): Response<Following>
}