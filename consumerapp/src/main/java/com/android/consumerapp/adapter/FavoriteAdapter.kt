package com.android.consumerapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.consumerapp.ui.detail.DetailActivity
import com.android.consumerapp.ui.detail.DetailActivity.Companion.KEY_TO_DETAIL
import com.android.consumerapp.R
import com.android.consumerapp.databinding.ItemGithubAccountBinding
import com.android.consumerapp.model.ResultItem
import com.android.consumerapp.utils.CustomOnItemClickListener
import com.bumptech.glide.Glide
import java.util.*

class FavoriteAdapter(private val activity: Activity) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var listResultItem = ArrayList<ResultItem>()
        set(listResultItem) {
            this.listResultItem.clear()
            this.listResultItem.addAll(listResultItem)
            notifyDataSetChanged()
        }

    inner class FavoriteViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val binding = ItemGithubAccountBinding.bind(itemView)
        fun bind(resultItem: ResultItem){
            binding.tvTitle.text = resultItem.login
            binding.tvId.text = resultItem.id.toString()
            Glide.with(itemView.context)
                    .load(resultItem.avatar_url)
                    .into(binding.imgPhoto)
            binding.cvItemGitUser.setOnClickListener(CustomOnItemClickListener(adapterPosition, object : CustomOnItemClickListener.OnItemClickCallback{
                override fun onItemClicked(view: View, position: Int) {
                    activity.startActivity(Intent(activity, DetailActivity::class.java).apply {
                        putExtra(KEY_TO_DETAIL,resultItem)
                    })
                }
            }))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_github_account, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listResultItem[position])
    }

    override fun getItemCount(): Int {
        return listResultItem.size
    }
}