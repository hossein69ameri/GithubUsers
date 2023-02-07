package com.example.githubusers.viewmodel.favorite

import DataStatus
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusers.data.database.UserEntity
import com.example.githubusers.data.repository.favorite.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    private val _stateList : MutableStateFlow<DataStatus<List<UserEntity>>?> = MutableStateFlow(null)
    val stateList = _stateList.asStateFlow()
    //get users list
    fun usersList() = viewModelScope.launch {
        favoriteRepository.usersList().collect {
            _stateList.value = DataStatus.success(it,it.isEmpty())
        }
    }

}