package com.example.githubusers.data.repository.detail

import com.example.githubusers.data.database.UserDao
import com.example.githubusers.data.database.UserEntity
import com.example.githubusers.data.model.ResponseDetail
import com.example.githubusers.data.model.ResponseFollowers
import com.example.githubusers.data.model.ResponseFollowing
import com.example.githubusers.data.remote.ApiServices
import com.example.githubusers.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailRepository @Inject constructor(private val apiServices: ApiServices,private val userDao: UserDao) {

    //get detail
    suspend fun detailUser(username: String): Flow<NetworkResult<ResponseDetail>> {
        return flow {
            if (apiServices.detailUser(username).isSuccessful) {
                    emit(NetworkResult.success(apiServices.detailUser(username).body()))
            }
        }
            .catch { emit(NetworkResult.error(it.message.toString())) }
            .flowOn(Dispatchers.IO)
    }
    //get follower
    suspend fun followersUser(username: String): Flow<NetworkResult<ResponseFollowers>> {
        return flow {
            if (apiServices.followersList(username).isSuccessful) {
                    emit(NetworkResult.success(apiServices.followersList(username).body()))
            }
        }
            .catch { emit(NetworkResult.error(it.message.toString())) }
            .flowOn(Dispatchers.IO)
    }
    //get following
    suspend fun followingUser(username: String): Flow<NetworkResult<ResponseFollowing>> {
        return flow {
            if (apiServices.followingList(username).isSuccessful) {
                    emit(NetworkResult.success(apiServices.followingList(username).body()))
            }
        }
            .catch { emit(NetworkResult.error(it.message.toString())) }
            .flowOn(Dispatchers.IO)
    }
    //save user
    suspend fun saveUser(entity: UserEntity) = userDao.saveUser(entity)
    //delete user
    suspend fun deleteUser(entity: UserEntity) = userDao.deleteUser(entity)
    //exist user
    fun existUser(id : Int) = userDao.existsUser(id)
}