package com.dicoding.aplikasigithubuser


import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.aplikasigithubuser.adapter.UserAdapter
import com.dicoding.aplikasigithubuser.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var settingPreferences: SettingPreferences
    private var users = ArrayList<String>()
    private lateinit var userList: List<ItemsItem>
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.users.observe(this, { item ->
            setData(item)
        })
        viewModel.isLoading.observe(this, {
            showLoading(it)
        })

        settingPreferences = SettingPreferences.getInstance(this.dataStore)
        val settingViewModelFactory = SettingViewModelFactory(settingPreferences)
        val settingViewModel = ViewModelProvider(this, settingViewModelFactory).get(SettingViewModel::class.java)
        settingViewModel.getThemeSettings().observe(this, { isDarkModeActive ->
            applyTheme(isDarkModeActive)
        })

    }

    private fun setData(users: List<ItemsItem>) {
        val listUser = ArrayList<String>()
        for (user in users) {
            listUser.add(
                """
                    ${user.avatarUrl};${user.login}
                """.trimIndent()
            )
        }
        userList = users
        val adapter = UserAdapter(userList)
        binding.rvUser.adapter = adapter
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                showDetailUser(data.login)
            }
        })

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showDetailUser(username: String){
        val moveIntent = Intent(this@MainActivity,DetailActivity::class.java)
        moveIntent.putExtra(DetailActivity.USERNAME, username)
        startActivity(moveIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                users.clear()
                viewModel.fetchGithubUsers(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                val intent = Intent(this, FavoriteUserActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun applyTheme(isDarkModeActive: Boolean) {
        if (isDarkModeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }



}

