package com.dicoding.aplikasigithubuser.database.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.aplikasigithubuser.database.local.entity.FavoriteUser

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)

    @Update
    fun update(favoriteUser: FavoriteUser)
    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT * from FavoriteUser ")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM FavoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>



}