package com.example.githubusers.data.database

import androidx.room.*
import com.example.githubusers.util.const.TABLE_USERS
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(entity: UserEntity)

    @Delete
    suspend fun deleteUser(entity: UserEntity)

    @Query("SELECT * FROM $TABLE_USERS")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM $TABLE_USERS WHERE id = :id)")
    fun existsUser(id: Int): Flow<Boolean>

}