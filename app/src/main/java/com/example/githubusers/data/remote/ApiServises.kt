package com.example.githubusers.data.remote

import com.example.githubusers.data.model.ResponseUsers
import com.example.githubusers.util.const.TOKEN
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiServises {

    @GET("/search/users")
    @Headers("Accept:application/vnd.github+json")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Header("authorization") auth: String = TOKEN
    ): Response<ResponseUsers>
}