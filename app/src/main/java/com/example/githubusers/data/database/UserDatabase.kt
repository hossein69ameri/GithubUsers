package com.example.githubusers.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 4, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun dao() : UserDao
}