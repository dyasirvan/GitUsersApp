package com.android.gitusers.ui.favorite

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.gitusers.R
import com.android.gitusers.adapter.FavoriteAdapter
import com.android.gitusers.databinding.FragmentFavoriteBinding
import com.android.gitusers.db.GitUserDatabase
import com.android.gitusers.model.ResultItemsSearch
import com.android.gitusers.repository.GitUserRepository
import com.android.gitusers.utils.IOnItemClickCallback

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root

        showProgressBar(true)
        loadData()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val gitUserRepository = context?.let { GitUserDatabase.invoke(it) }?.let { GitUserRepository(it) }
                val data = favoriteAdapter.getData(position)
                if (gitUserRepository != null) {
                    val alertDialog = AlertDialog.Builder(context)
                    alertDialog.setTitle(getString(R.string.alert_title))
                    alertDialog.setMessage(getString(R.string.alert_message))

                    alertDialog.setPositiveButton(getString(R.string.alert_btn_yes)) { _, _ ->
                        favoriteViewModel.deleteDataUser(data, gitUserRepository)
                        Toast.makeText(context, getString(R.string.delete_message), Toast.LENGTH_LONG).show()
                    }
                    alertDialog.setCancelable(false)
                    alertDialog.setNegativeButton(getString(R.string.alert_btn_no)) { dialog, _ ->
                        dialog.cancel()
                        showProgressBar(true)
                        loadData()
                    }
                    alertDialog.show()
                }

            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvFavorite)
        }

        return view
    }

    private fun loadData(){
        val gitUserRepository = context?.let { GitUserDatabase.invoke(it) }?.let { GitUserRepository(it) }
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)

        if (gitUserRepository != null) {
            favoriteViewModel.getDataUser(gitUserRepository).observe({lifecycle}, {
                binding.rvFavorite.apply {
                    favoriteAdapter = FavoriteAdapter(it)
                    this.adapter = favoriteAdapter
                    this.layoutManager = LinearLayoutManager(context)
                    favoriteAdapter.notifyDataSetChanged()
                    showProgressBar(false)
                    goToDetailPage()
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

    private fun goToDetailPage(){
        favoriteAdapter.setOnItemClickCallback(object : IOnItemClickCallback {
            override fun onItemClicked(data: ResultItemsSearch) {
                val bundle = Bundle().apply {
                    putParcelable("detail", data)
                }
                findNavController().navigate(
                        R.id.action_favoriteFragment_to_detailActivity,
                        bundle
                )
            }
        })
    }

}