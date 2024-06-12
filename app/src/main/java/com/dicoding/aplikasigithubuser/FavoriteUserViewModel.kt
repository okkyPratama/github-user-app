package com.dicoding.aplikasigithubuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.aplikasigithubuser.database.FavoriteUserRepository
import com.dicoding.aplikasigithubuser.database.local.entity.FavoriteUser

class FavoriteUserViewModel(private val favoriteUserRepository: FavoriteUserRepository): ViewModel() {
  fun getAllFavoriteUser() = favoriteUserRepository.getAllFavoriteUser()

  fun insert(favoriteUser: FavoriteUser) {
    favoriteUserRepository.insert(favoriteUser)
  }

  fun delete(favoriteUser: FavoriteUser) {
    favoriteUserRepository.delete(favoriteUser)
  }


  fun getFavoriteUserByUsername(favoriteUser: String): LiveData<FavoriteUser> {
    return favoriteUserRepository.getFavoriteUserByUsername(favoriteUser)
  }

}