package com.example.githubusers.data.remote

import com.example.githubusers.data.model.ResponseDetail
import com.example.githubusers.data.model.ResponseUsers
import com.example.githubusers.util.const.TOKEN
import retrofit2.Response
import retrofit2.http.*

interface ApiServises {

    @Headers("Accept: application/vnd.github+json")
    @GET("/search/users")
    suspend fun searchUsers(
        @Header("Authorization") auth : String = TOKEN,
        @Query("q") username:String
    ): Response<ResponseUsers>


    @Headers("Accept: application/vnd.github+json")
    @GET("/users/{username}")
    suspend fun detailUser(@Path("username") username:String): Response<ResponseDetail>
}