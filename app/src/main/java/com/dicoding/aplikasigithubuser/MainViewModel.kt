package com.dicoding.aplikasigithubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.aplikasigithubuser.database.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response



class MainViewModel : ViewModel() {

    private val _users = MutableLiveData<List<ItemsItem>>()
    val users: LiveData<List<ItemsItem>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
        private const val USER = "george"
    }

    init {
        fetchGithubUsers(USER)
    }

     fun fetchGithubUsers(q: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getGithubUser(q)
        client.enqueue(object : retrofit2.Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _users.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()} kode error")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }




}
