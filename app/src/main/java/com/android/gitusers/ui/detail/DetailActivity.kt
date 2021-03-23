package com.android.gitusers.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.navArgs
import com.android.gitusers.MainActivity
import com.android.gitusers.R
import com.android.gitusers.databinding.ActivityDetailBinding
import com.android.gitusers.db.GitUserDatabase
import com.android.gitusers.model.ResultItemsSearch
import com.android.gitusers.repository.GitUserRepository
import com.android.gitusers.ui.follower.FollowerFragment
import com.android.gitusers.ui.following.FollowingFragment
import com.android.gitusers.utils.Constants.Companion.KEY_USERNAME
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var detail: ResultItemsSearch
    private val args: DetailActivityArgs by navArgs()
    private lateinit var detailViewModel: DetailViewModel
    private var frameLayout: FrameLayout? = null
    private var fragment: Fragment? = null
    private var fragmentManager: FragmentManager? = null
    private var fragmentTransaction: FragmentTransaction? = null
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }
    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()
        showProgressBar(true)
        detail = args.detail

        tabLayout = binding.tabs
        frameLayout = binding.frameLayout

//        load data from view model
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        detailViewModel.detailDataUser((detail.login?:0) as String)
        detailViewModel.data.observe({lifecycle}, {
            binding.tvName.text = it.name
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

        val gitUserRepository = GitUserRepository(GitUserDatabase.invoke(this))
        binding.fabFav.setOnClickListener {
            detailViewModel.saveData(detail, gitUserRepository)
            Toast.makeText(this@DetailActivity, getString(R.string.success_saved), Toast.LENGTH_LONG).show()
        }

        binding.fabShare.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "${getString(R.string.message_share)} ${detail.html_url}")
                type = "text/plain"
            }
            Intent.createChooser(intent, getString(R.string.share_with))
            startActivity(intent)
        }

//        button back to main activity
        binding.btnClose.setOnClickListener {
            finish()
            Intent(this@DetailActivity, MainActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun onExpandedButton() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if(!clicked){
            binding.fabFav.startAnimation(fromBottom)
            binding.fabShare.startAnimation(fromBottom)
            binding.fabExpand.startAnimation(rotateOpen)
        }else{
            binding.fabFav.startAnimation(toBottom)
            binding.fabShare.startAnimation(toBottom)
            binding.fabExpand.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked){
            binding.fabFav.visibility = View.VISIBLE
            binding.fabShare.visibility = View.VISIBLE
        }else{
            binding.fabFav.visibility = View.INVISIBLE
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
        if(!clicked){
            binding.fabFav.isClickable = true
            binding.fabShare.isClickable = true
        }else{
            binding.fabFav.isClickable = false
            binding.fabShare.isClickable = false
        }
    }

}