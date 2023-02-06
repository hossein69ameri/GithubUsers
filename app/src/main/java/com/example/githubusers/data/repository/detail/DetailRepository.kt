package com.example.githubusers.data.repository.detail

import com.example.githubusers.data.model.ResponseDetail
import com.example.githubusers.data.model.ResponseFollowers
import com.example.githubusers.data.model.ResponseFollowing
import com.example.githubusers.data.remote.ApiServices
import com.example.githubusers.util.NetworkResult
import com.example.githubusers.util.const.RECOURSE_NOT_FOUND
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailRepository @Inject constructor(private val apiServices: ApiServices) {

    suspend fun detailUser(username: String): Flow<NetworkResult<ResponseDetail>> {
        return flow {
            when (apiServices.detailUser(username).code()) {
                200 -> {
                    emit(NetworkResult.success(apiServices.detailUser(username).body()))
                }

                404 -> {
                    emit(NetworkResult.error(RECOURSE_NOT_FOUND))
                }
            }
        }
            .catch { emit(NetworkResult.error(it.message.toString())) }
            .flowOn(Dispatchers.IO)
    }

    suspend fun followersUser(username: String): Flow<NetworkResult<ResponseFollowers>> {
        return flow {
            when (apiServices.followersList(username).code()) {
                200 -> {
                    emit(NetworkResult.success(apiServices.followersList(username).body()))
                }

                404 -> {
                    emit(NetworkResult.error(RECOURSE_NOT_FOUND))
                }
            }
        }
            .catch { emit(NetworkResult.error(it.message.toString())) }
            .flowOn(Dispatchers.IO)
    }

    suspend fun followingUser(username: String): Flow<NetworkResult<ResponseFollowing>> {
        return flow {
            when (apiServices.followingList(username).code()) {
                200 -> {
                    emit(NetworkResult.success(apiServices.followingList(username).body()))
                }

                404 -> {
                    emit(NetworkResult.error(RECOURSE_NOT_FOUND))
                }
            }
        }
            .catch { emit(NetworkResult.error(it.message.toString())) }
            .flowOn(Dispatchers.IO)
    }
}