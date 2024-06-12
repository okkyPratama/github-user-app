package com.dicoding.aplikasigithubuser

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.aplikasigithubuser.adapter.UserAdapter
import com.dicoding.aplikasigithubuser.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding

    private var position: Int = 0
    private var username: String? = null
    private lateinit var detailViewModel: DetailViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }
        detailViewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
        detailViewModel.followLoading.observe(viewLifecycleOwner, { isLoading ->
            showLoading(isLoading)
        })
        if (position == 1) {
            detailViewModel.fetchFollowersData(username!!)
            detailViewModel.followers.observe(viewLifecycleOwner) { followers ->
                setData(followers)
            }
        } else {
            detailViewModel.fetchFollowingData(username!!)
            detailViewModel.following.observe(viewLifecycleOwner) { following ->
                setData(following)
            }
        }


    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun setData(listFollow: List<ItemsItem>) {
        binding.apply {
            binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())
            val adapter = UserAdapter(listFollow)
            binding.rvFollow.adapter = adapter
            adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ItemsItem) {}
            })

        }
    }

    companion object {
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "arg_username"

    }


}