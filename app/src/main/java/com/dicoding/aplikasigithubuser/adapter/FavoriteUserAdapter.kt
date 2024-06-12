package com.dicoding.aplikasigithubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.aplikasigithubuser.R
import com.dicoding.aplikasigithubuser.database.local.entity.FavoriteUser
import de.hdodenhof.circleimageview.CircleImageView

class FavoriteUserAdapter(private val listUser: List<FavoriteUser>) :
    RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

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
        viewHolder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(user)
        }


    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteUser)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun getItemCount(): Int = listUser.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val profileImage: CircleImageView = itemView.findViewById(R.id.profile_image)
        private val username: TextView = itemView.findViewById(R.id.username)


        fun bind(item: FavoriteUser) {
            Glide.with(itemView.context)
                .load(item.avatarUrl)
                .placeholder(R.drawable.ic_baseline_person_24)
                .error(R.drawable.ic_baseline_person_24)
                .into(profileImage)
            username.text = item.username
        }


    }
}