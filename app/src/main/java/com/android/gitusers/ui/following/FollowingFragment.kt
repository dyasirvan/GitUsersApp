package com.android.gitusers.ui.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.gitusers.adapter.FollowingAdapter
import com.android.gitusers.databinding.FragmentFollowingBinding
import com.android.gitusers.utils.Constants.Companion.KEY_USERNAME

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var followingAdapter: FollowingAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View?{
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val view = binding.root
        showProgressBar(true)

        val bundle = arguments
        val username = bundle!!.getString(KEY_USERNAME)
        followingViewModel = ViewModelProviders.of(this).get(FollowingViewModel::class.java)
        followingViewModel.getFollowing(username!!)
        followingViewModel.data.observe({lifecycle}, {
            if(it.isEmpty()){
                textViewNotFollowingVisibility(true)
                showProgressBar(false)
            }else{
                textViewNotFollowingVisibility(false)
                binding.rvFollowing.apply {
                    followingAdapter = FollowingAdapter(it)
                    this.adapter = followingAdapter
                    this.layoutManager = LinearLayoutManager(context)
                    followingAdapter.notifyDataSetChanged()
                    showProgressBar(false)
                }
            }
        })
        return view
    }

    private fun textViewNotFollowingVisibility(state: Boolean){
        if(state){
            binding.tvNotFollowing.visibility = View.VISIBLE
            binding.rvFollowing.visibility = View.GONE
        }else{
            binding.tvNotFollowing.visibility = View.GONE
            binding.rvFollowing.visibility = View.VISIBLE
        }
    }

    private fun showProgressBar(state: Boolean){
        if(state){
            binding.progressBarFollowing.visibility = View.VISIBLE
        }else{
            binding.progressBarFollowing.visibility = View.GONE
        }
    }
}