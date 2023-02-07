package com.example.githubusers.data.repository.favorite

import com.example.githubusers.data.database.UserDao
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val userDao: UserDao){
    //users list
    fun usersList() = userDao.getAllUsers()
}