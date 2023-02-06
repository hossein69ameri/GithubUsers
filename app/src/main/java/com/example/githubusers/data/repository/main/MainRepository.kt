package com.example.githubusers.data.repository.main

import com.example.githubusers.data.model.ResponseUsers
import com.example.githubusers.data.remote.ApiServises
import com.example.githubusers.util.NetworkResult
import com.example.githubusers.util.const.TOKEN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiServises: ApiServises) {

    suspend fun searchUsers(auth : String , query: String): Flow<NetworkResult<ResponseUsers>> {
        return flow {
            when (apiServises.searchUsers(auth,query).code()) {
                200 -> {
                    emit(NetworkResult.success(apiServises.searchUsers(auth,query).body()))
                }
                304 -> {
                    emit(NetworkResult.error("Not modified"))
                }
                422 -> {
                    emit(NetworkResult.error("Validation failed, or the endpoint has been spammed."))
                }
                503 -> {
                    emit(NetworkResult.error("Service unavailable"))
                }
            }
        }
            .catch { emit(NetworkResult.error(it.message.toString())) }
            .flowOn(Dispatchers.IO)
    }
}