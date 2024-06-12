package com.dicoding.aplikasigithubuser.di

import android.content.Context
import com.dicoding.aplikasigithubuser.database.remote.retrofit.ApiConfig
import com.dicoding.aplikasigithubuser.database.FavoriteUserRepository
import com.dicoding.aplikasigithubuser.database.room.FavoriteUserDatabase
import com.dicoding.aplikasigithubuser.utils.AppExecutors

object Injection {

    fun provideRepository(context: Context): FavoriteUserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteUserDatabase.getDatabase(context)
        val dao = database.favoriteUserDao()
        val appExecutors = AppExecutors()
        return FavoriteUserRepository.getInstance(apiService, dao, appExecutors)
    }
}