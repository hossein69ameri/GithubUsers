package com.example.githubusers.data.remote

import com.example.githubusers.data.model.ResponseDetail
import com.example.githubusers.data.model.ResponseFollowers
import com.example.githubusers.data.model.ResponseFollowing
import com.example.githubusers.data.model.ResponseUsers
import com.example.githubusers.util.const.TOKEN
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {

    //search username
    @GET("/search/users")
    suspend fun searchUsers(@Query("q") username:String): Response<ResponseUsers>

    //get detail username
    @GET("/users/{username}")
    suspend fun detailUser(@Path("username") username:String): Response<ResponseDetail>

    //get followers
    @GET("/users/{username}/followers")
    suspend fun followersList(@Path("username") username:String) : Response<ResponseFollowers>

    //get following
    @GET("/users/{username}/following")
    suspend fun followingList(@Path("username") username:String) : Response<ResponseFollowing>


}