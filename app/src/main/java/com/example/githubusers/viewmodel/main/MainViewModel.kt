package com.example.githubusers.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusers.data.model.ResponseUsers
import com.example.githubusers.data.repository.main.MainRepository
import com.example.githubusers.util.NetworkResult
import com.example.githubusers.util.const.TOKEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _stateSearch : MutableStateFlow<NetworkResult<ResponseUsers>?> = MutableStateFlow(null)
    val stateSearch = _stateSearch.asStateFlow()

    fun searchUsers(auth : String , query : String) = viewModelScope.launch {
        mainRepository.searchUsers(auth,query).collect{
            _stateSearch.value = it
        }
    }
}