package com.android.consumerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.consumerapp.databinding.ItemGithubAccountBinding
import com.android.consumerapp.model.ResultItem
import com.android.consumerapp.utils.IOnItemClickCallback
import com.bumptech.glide.Glide

class FavoriteAdapter(private val listAccount: List<ResultItem>) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private lateinit var onItemClickCallback: IOnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: IOnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun getData(position: Int): ResultItem {
        return listAccount[position]
    }

    inner class FavoriteViewHolder(val binding: ItemGithubAccountBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemGithubAccountBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        with(holder){
            with(listAccount[position]){
                binding.tvTitle.text = login
                binding.tvId.text = id.toString()
                Glide.with(holder.itemView.context)
                    .load(avatar_url)
                    .into(binding.imgPhoto)
                holder.itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(listAccount[holder.adapterPosition])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listAccount.size
    }
}