package com.example.githubusers.data.repository.detail

import com.example.githubusers.data.model.ResponseDetail
import com.example.githubusers.data.remote.ApiServises
import com.example.githubusers.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailRepository @Inject constructor(private val apiServises: ApiServises) {

    suspend fun detailUser(username: String): Flow<NetworkResult<ResponseDetail>> {
        return flow {
            when (apiServises.detailUser(username).code()) {
                200 -> {
                    emit(NetworkResult.success(apiServises.detailUser(username).body()))
                }

                404 -> {
                    emit(NetworkResult.error("Recourse Not Found"))
                }
            }
        }
            .catch { emit(NetworkResult.error(it.message.toString())) }
            .flowOn(Dispatchers.IO)
    }
}