package com.example.githubusers.viewmodel.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusers.data.model.ResponseDetail
import com.example.githubusers.data.model.ResponseFollowers
import com.example.githubusers.data.model.ResponseFollowing
import com.example.githubusers.data.repository.detail.DetailRepository
import com.example.githubusers.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val detailRepository: DetailRepository) : ViewModel() {

    private val _stateDetail : MutableStateFlow<NetworkResult<ResponseDetail>?> = MutableStateFlow(null)
    val stateDetail = _stateDetail.asStateFlow()

    private val _stateFollowers : MutableStateFlow<NetworkResult<ResponseFollowers>?> = MutableStateFlow(null)
    val stateFollowers = _stateFollowers.asStateFlow()

    private val _stateFollowing : MutableStateFlow<NetworkResult<ResponseFollowing>?> = MutableStateFlow(null)
    val stateFollowing = _stateFollowing.asStateFlow()

    fun detailUser(username : String) = viewModelScope.launch {
        detailRepository.detailUser(username).collect{
            _stateDetail.value = it
        }
    }

    fun followerslUser(username : String) = viewModelScope.launch {
        detailRepository.followersUser(username).collect{
            _stateFollowers.value = it
        }
    }

    fun followinglUser(username : String) = viewModelScope.launch {
        detailRepository.followingUser(username).collect{
            _stateFollowing.value = it
        }
    }
}