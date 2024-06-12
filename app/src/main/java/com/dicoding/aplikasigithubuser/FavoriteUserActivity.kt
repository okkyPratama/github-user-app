package com.dicoding.aplikasigithubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.aplikasigithubuser.adapter.FavoriteUserAdapter
import com.dicoding.aplikasigithubuser.database.local.entity.FavoriteUser
import com.dicoding.aplikasigithubuser.databinding.ActivityFavoriteUserBinding
import com.dicoding.aplikasigithubuser.di.Injection

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val repository = Injection.provideRepository(this)
        val factory = ViewModelFactory(repository)
        favoriteUserViewModel = ViewModelProvider(this, factory).get(FavoriteUserViewModel::class.java)

        binding.rvFavoriteUsers.layoutManager = LinearLayoutManager(this)

        favoriteUserViewModel.getAllFavoriteUser().observe(this, { favoriteUsers ->
            val items = arrayListOf<FavoriteUser>()
            favoriteUsers.map {
                val item = FavoriteUser(username = it.username, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            val adapter = FavoriteUserAdapter(items)
            binding.rvFavoriteUsers.adapter = adapter

            adapter.setOnItemClickCallback(object : FavoriteUserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: FavoriteUser) {
                    showDetailUser(data.username)
                }
            })

        })

    }

    private fun showDetailUser(username: String){
        val moveIntent = Intent(this@FavoriteUserActivity,DetailActivity::class.java)
        moveIntent.putExtra(DetailActivity.USERNAME, username)
        startActivity(moveIntent)
    }



}