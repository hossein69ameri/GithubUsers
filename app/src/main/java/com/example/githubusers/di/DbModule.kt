package com.example.githubusers.di

import android.content.Context
import androidx.room.Room
import com.example.githubusers.data.database.UserDatabase
import com.example.githubusers.data.database.UserEntity
import com.example.githubusers.util.const.DATABASE_USERS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, UserDatabase::class.java, DATABASE_USERS
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db: UserDatabase) = db.dao()

    @Provides
    @Singleton
    fun provideEntity() = UserEntity()
}