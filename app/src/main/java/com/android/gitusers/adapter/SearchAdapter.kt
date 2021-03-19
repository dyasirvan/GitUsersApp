package com.android.gitusers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.gitusers.databinding.ItemGithubAccountBinding
import com.android.gitusers.model.ResultItemsSearch
import com.android.gitusers.utils.IOnItemClickCallback
import com.bumptech.glide.Glide

class SearchAdapter(private val listAccount: List<ResultItemsSearch>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private lateinit var onItemClickCallback: IOnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: IOnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class SearchViewHolder(val binding: ItemGithubAccountBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemGithubAccountBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
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