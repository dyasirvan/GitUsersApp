package com.android.consumerapp.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.android.consumerapp.R
import com.android.consumerapp.databinding.ActivityDetailBinding
import com.android.consumerapp.model.ResultItem
import com.android.consumerapp.ui.follower.FollowerFragment
import com.android.consumerapp.ui.following.FollowingFragment
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout

class DetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var tabLayout: TabLayout
    private var detail: ResultItem? = null
    private lateinit var detailViewModel: DetailViewModel
    private var frameLayout: FrameLayout? = null
    private var fragment: Fragment? = null
    private var fragmentManager: FragmentManager? = null
    private var fragmentTransaction: FragmentTransaction? = null
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this,
        R.anim.rotate_open_anim
    ) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this,
        R.anim.rotate_close_anim
    ) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this,
        R.anim.from_bottom_anim
    ) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this,
        R.anim.to_bottom_anim
    ) }
    private var clicked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = "Detail"
        supportActionBar?.elevation = 0F
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        tabLayout = binding.tabs
        frameLayout = binding.frameLayout
        showProgressBar(true)

        detail = intent.getParcelableExtra(KEY_TO_DETAIL)
        if(detail != null){
            
            detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
            detailViewModel.detailDataUser((detail?.login?:0).toString())
            detailViewModel.data.observe({lifecycle},{
                binding.tvName.text = it.login
                binding.tvAddress.text = it.location
                Glide.with(this)
                    .load(it.avatar_url)
                    .into(binding.imgPhoto)

                tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.follower) + "\n ${it.followers}"))
                tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.following) + "\n ${it.following}"))


//            progress bar off
                showProgressBar(false)

//           default tab layout
                val bundle = Bundle().apply {
                    putString(KEY_USERNAME, it.login)
                }
                fragment = FollowerFragment()
                fragmentManager = supportFragmentManager
                fragmentTransaction = fragmentManager!!.beginTransaction()
                (fragment as FollowerFragment).arguments = bundle
                fragmentTransaction!!.replace(R.id.frame_layout, fragment as FollowerFragment)
                fragmentTransaction!!.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                fragmentTransaction!!.commit()

//            move fragment
                tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                    override fun onTabSelected(tab: TabLayout.Tab?) {

                        if (tab != null) {
                            when(tab.position){
                                0->fragment = FollowerFragment()
                                1->fragment = FollowingFragment()
                            }
                            val fm = supportFragmentManager
                            val ft = fm.beginTransaction()
                            fragment!!.arguments = bundle
                            ft.replace(R.id.frame_layout, fragment!!)
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            ft.commit()
                        }
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {

                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {

                    }
                })

//            button share and favorite
                binding.fabExpand.setOnClickListener {
                    onExpandedButton()
                }
            })
        }
        Log.d("DetailActivity", "onCreate: $detail")
    }

    private fun onExpandedButton() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if(!clicked){
            binding.fabShare.startAnimation(fromBottom)
            binding.fabExpand.startAnimation(rotateOpen)
        }else{
            binding.fabShare.startAnimation(toBottom)
            binding.fabExpand.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked){
            binding.fabShare.visibility = View.VISIBLE
        }else{
            binding.fabShare.visibility = View.INVISIBLE
        }
    }

    private fun showProgressBar(state: Boolean){
        if(state){
            binding.progressBarDetail.visibility = View.VISIBLE
        }else{
            binding.progressBarDetail.visibility = View.GONE
        }
    }

    private fun setClickable(clicked: Boolean){
        binding.fabShare.isClickable = !clicked
    }
    
    companion object{
        const val KEY_TO_DETAIL = "id_detail"
        const val KEY_USERNAME = "key_username"
    }
}