package com.android.gitusers.api

import com.android.gitusers.model.*
import com.android.gitusers.utils.Constants.Companion.TOKEN_GITHUB
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @GET("/search/users")
    suspend fun searchAccount(
        @Query("q") username: String,
        @Header("Authorization") token: String= TOKEN_GITHUB
    ): ResponseSearch

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