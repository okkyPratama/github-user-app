package com.dicoding.aplikasigithubuser.database.remote.retrofit

import com.dicoding.aplikasigithubuser.database.remote.retrofit.response.DetailUserResponse
import com.dicoding.aplikasigithubuser.GithubResponse
import com.dicoding.aplikasigithubuser.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getGithubUser(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}


