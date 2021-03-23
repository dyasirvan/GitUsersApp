package com.android.consumerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.consumerapp.databinding.ItemGithubAccountBinding
import com.android.consumerapp.model.FollowersItem
import com.bumptech.glide.Glide

class FollowersAdapter(private val listAccount: List<FollowersItem>) : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {

    inner class FollowersViewHolder(val binding: ItemGithubAccountBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val binding = ItemGithubAccountBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        with(holder){
            with(listAccount[position]) {
                binding.tvTitle.text = login
                binding.tvId.text = id.toString()
                Glide.with(holder.itemView.context)
                        .load(avatar_url)
                        .into(binding.imgPhoto)
            }
        }
    }

    override fun getItemCount(): Int {
        return if(listAccount.size > 10){
            10
        }else{
            listAccount.size
        }
    }
}