package com.example.githubusers.data.repository.main

import com.example.githubusers.data.model.ResponseUsers
import com.example.githubusers.data.remote.ApiServices
import com.example.githubusers.util.NetworkResult
import com.example.githubusers.util.const.NOT_MODIFIED
import com.example.githubusers.util.const.SERVICE_UNAVAILABLE
import com.example.githubusers.util.const.VALIDATION_FAILED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiServices: ApiServices) {
    //search users
    suspend fun searchUsers(query: String): Flow<NetworkResult<ResponseUsers>> {
        return flow {
            if (apiServices.searchUsers(query).isSuccessful) {
                emit(NetworkResult.success(apiServices.searchUsers(query).body()))
            }
        }
            .catch { emit(NetworkResult.error(it.message.toString())) }
            .flowOn(Dispatchers.IO)
    }
}