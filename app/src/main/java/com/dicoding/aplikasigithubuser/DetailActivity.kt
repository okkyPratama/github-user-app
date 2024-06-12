package com.dicoding.aplikasigithubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.aplikasigithubuser.adapter.SectionsPagerAdapter
import com.dicoding.aplikasigithubuser.database.local.entity.FavoriteUser
import com.dicoding.aplikasigithubuser.databinding.ActivityDetailBinding
import com.dicoding.aplikasigithubuser.di.Injection
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Detail Github User"

        val username = intent.getStringExtra(USERNAME)
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)


        val repository = Injection.provideRepository(this)
        val factory = ViewModelFactory(repository)
        favoriteUserViewModel = ViewModelProvider(this, factory).get(FavoriteUserViewModel::class.java)
        observeFavoriteStatus(username!!)


        detailViewModel.fetchUserDetail(username!!)
        detailViewModel.detailUser.observe(this, { user ->
            Glide.with(this)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.profilePic)

            binding.apply {
                login.text = user.login
                name.text = user.name
                followers.text = user.followers.toString()
                following.text = user.following.toString()
            }

            binding.fabFavorite.setOnClickListener {
                val favoriteUser = FavoriteUser(username = user.login!!, avatarUrl = user.avatarUrl)
                val isFavorited = it.tag as Boolean

                if (isFavorited) {
                    favoriteUserViewModel.delete(favoriteUser)
                } else {
                    favoriteUserViewModel.insert(favoriteUser)
                }
            }


        })



        detailViewModel.isLoading.observe(this, {
            showLoading(it)
        })
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun observeFavoriteStatus(username: String) {
        favoriteUserViewModel.getFavoriteUserByUsername(username).observe(this, { favoriteUser ->
            if (favoriteUser == null) {
                binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                binding.fabFavorite.tag = false
            } else {
                binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_full_24)
                binding.fabFavorite.tag = true
            }
        })
    }

    companion object {
        const val USERNAME = "username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers_txt,
            R.string.following_txt
        )
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}