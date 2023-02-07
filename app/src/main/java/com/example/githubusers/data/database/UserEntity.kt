package com.example.githubusers.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubusers.util.const.TABLE_USERS

@Entity(tableName = TABLE_USERS)
data class UserEntity(
    @PrimaryKey
    var id: Int = 0,
    var name: String = "",
    var login: String = "",
    var location: String = "",
    var bio: String = "",
    var follower: String = "",
    var following: String = "",
    var repo: String = "",
    var image: String = "",
)
