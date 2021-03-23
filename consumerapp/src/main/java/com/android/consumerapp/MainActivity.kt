package com.android.consumerapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.consumerapp.adapter.FavoriteAdapter
import com.android.consumerapp.databinding.ActivityMainBinding
import com.android.consumerapp.db.GitUserDatabase
import com.android.consumerapp.repository.GitUserRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        showProgressBar(true)
        loadData()
    }
    private fun loadData(){
        val gitUserRepository = applicationContext?.let { GitUserDatabase.invoke(it) }?.let { GitUserRepository(it) }
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        if (gitUserRepository != null) {
            favoriteViewModel.getDataUser(gitUserRepository).observe({lifecycle}, {
                binding.rvFavorite.apply {
                    favoriteAdapter = FavoriteAdapter(it)
                    this.adapter = favoriteAdapter
                    this.layoutManager = LinearLayoutManager(context)
                    favoriteAdapter.notifyDataSetChanged()
                    showProgressBar(false)
//                    goToDetailPage()
                }
            })
        }

    }

    private fun showProgressBar(state: Boolean){
        if(state){
            binding.progressBarFavorite.visibility = View.VISIBLE
        }else{
            binding.progressBarFavorite.visibility = View.GONE
        }
    }
}