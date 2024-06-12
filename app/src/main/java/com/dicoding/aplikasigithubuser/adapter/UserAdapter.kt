package com.dicoding.aplikasigithubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.aplikasigithubuser.ItemsItem
import com.dicoding.aplikasigithubuser.R
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter( var listUser: List<ItemsItem>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.item_row_user,
                viewGroup,
                false
            )
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = listUser[position]
        viewHolder.bind(user)
        onItemClickCallback.let {
            viewHolder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(user)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun getItemCount(): Int = listUser.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val profileImage: CircleImageView = itemView.findViewById(R.id.profile_image)
        private val username: TextView = itemView.findViewById(R.id.username)

        fun bind(item: ItemsItem) {
            Glide.with(itemView.context)
                .load(item.avatarUrl)
                .placeholder(R.drawable.ic_baseline_person_24)
                .error(R.drawable.ic_baseline_person_24)
                .into(profileImage)
            username.text = item.login
        }
    }
}

