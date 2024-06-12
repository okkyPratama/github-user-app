package com.dicoding.aplikasigithubuser.database

import androidx.datastore.dataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.aplikasigithubuser.database.remote.retrofit.ApiService
import com.dicoding.aplikasigithubuser.database.local.entity.FavoriteUser
import com.dicoding.aplikasigithubuser.database.room.FavoriteUserDao
import com.dicoding.aplikasigithubuser.utils.AppExecutors

class FavoriteUserRepository private constructor(

    private val apiService: ApiService,
    private val appExecutors: AppExecutors,
    private val favoriteUserDao: FavoriteUserDao,


    ) {

    private val result = MediatorLiveData<Result<List<FavoriteUser>>>()

fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> {
    return favoriteUserDao.getAllFavoriteUser()
}

    fun insert(favoriteUser: FavoriteUser) {
        appExecutors.diskIO.execute { favoriteUserDao.insert(favoriteUser) }
    }
    fun delete(favoriteUser: FavoriteUser) {
        appExecutors.diskIO.execute { favoriteUserDao.delete(favoriteUser) }
    }

    fun update(favoriteUser: FavoriteUser) {
        appExecutors.diskIO.execute  { favoriteUserDao.update(favoriteUser) }
    }

    fun getFavoriteUserByUsername(favoriteUser: String): LiveData<FavoriteUser> {
        return favoriteUserDao.getFavoriteUserByUsername(favoriteUser)
    }



    companion object {
        @Volatile
        private var instance: FavoriteUserRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteUserDao: FavoriteUserDao,
            appExecutors: AppExecutors
        ): FavoriteUserRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteUserRepository(apiService, appExecutors , favoriteUserDao)
            }.also { instance = it }
    }
}


