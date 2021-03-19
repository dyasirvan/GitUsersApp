package com.android.gitusers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.gitusers.databinding.ItemGithubAccountBinding
import com.android.gitusers.model.FollowingItem
import com.bumptech.glide.Glide

class FollowingAdapter(private val listAccount: List<FollowingItem>) : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    inner class FollowingViewHolder(val binding: ItemGithubAccountBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val binding = ItemGithubAccountBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
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