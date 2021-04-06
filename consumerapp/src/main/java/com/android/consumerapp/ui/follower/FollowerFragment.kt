package com.android.consumerapp.ui.follower

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.consumerapp.adapter.FollowersAdapter
import com.android.consumerapp.databinding.FragmentFollowerBinding
import com.android.consumerapp.ui.detail.DetailActivity.Companion.KEY_USERNAME

class FollowerFragment : Fragment() {
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private lateinit var followerViewModel: FollowerViewModel
    private lateinit var followersAdapter: FollowersAdapter

    override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
    ): View?{
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        val view = binding.root
        showProgressBar(true)

        val bundle = arguments
        val username = bundle!!.getString(KEY_USERNAME)
        Log.d("FollowerFragment", "onCreateView: $username")
        followerViewModel = ViewModelProvider(this).get(FollowerViewModel::class.java)

        followerViewModel.getFollower(username?:"")

        followerViewModel.data.observe({lifecycle}, {
            if(it.isEmpty()){
                textViewNotExistVisibility(true)
                showProgressBar(false)
            }else{
                textViewNotExistVisibility(false)
                binding.rvFollower.apply {
                    followersAdapter = FollowersAdapter(it)
                    this.adapter = followersAdapter
                    this.layoutManager = LinearLayoutManager(context)
                    followersAdapter.notifyDataSetChanged()
                    showProgressBar(false)
                }
            }
        })
        return view
    }

    private fun textViewNotExistVisibility(state: Boolean){
        if(state){
            binding.tvNotExist.visibility = View.VISIBLE
            binding.rvFollower.visibility = View.GONE
        }else{
            binding.tvNotExist.visibility = View.GONE
            binding.rvFollower.visibility = View.VISIBLE
        }
    }

    private fun showProgressBar(state: Boolean){
        if(state){
            binding.progressBarFollower.visibility = View.VISIBLE
        }else{
            binding.progressBarFollower.visibility = View.GONE
        }
    }
}