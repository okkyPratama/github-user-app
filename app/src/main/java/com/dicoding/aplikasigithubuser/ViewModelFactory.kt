package com.dicoding.aplikasigithubuser

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.aplikasigithubuser.database.FavoriteUserRepository
import com.dicoding.aplikasigithubuser.di.Injection

class ViewModelFactory constructor( val favoriteUserRepository: FavoriteUserRepository) :
    ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)) {
            return FavoriteUserViewModel(favoriteUserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}

