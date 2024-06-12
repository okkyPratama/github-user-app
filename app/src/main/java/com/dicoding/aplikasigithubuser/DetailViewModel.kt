package com.dicoding.aplikasigithubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.aplikasigithubuser.database.remote.retrofit.ApiConfig
import com.dicoding.aplikasigithubuser.database.remote.retrofit.response.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following

    private val _followLoading = MutableLiveData<Boolean>()
    val followLoading: LiveData<Boolean> = _followLoading

    companion object {
        private const val TAG = "DetailViewModel"
    }


     fun fetchUserDetail(name: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetailUser(name)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(DetailViewModel.TAG, "onFailure: ${t.message.toString()}")
            }

        })

    }

    fun fetchFollowersData(username: String) {
        _followLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    _followers.value = response.body()
                    _followLoading.value = false
                } else {
                    _followLoading.value = false
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _followLoading.value = false
            }
        })
    }

    fun fetchFollowingData(username: String){
        _followLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    _followLoading.value = false
                    _following.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _followLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }


}