package com.android.gitusers.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.gitusers.R
import com.android.gitusers.adapter.SearchAdapter
import com.android.gitusers.databinding.FragmentSearchBinding
import com.android.gitusers.model.ResultItemsSearch
import com.android.gitusers.utils.Constants.Companion.SEARCH_TIME_DELAY
import com.android.gitusers.utils.IOnItemClickCallback
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        showProgressBar(true)
        loadJSON()
        var job: Job? = null
        val username = binding.etSearch
        username.clearFocus()
        username.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                job?.cancel()
                job = MainScope().launch {
                    delay(SEARCH_TIME_DELAY)
                    s?.let {
                        if(s.toString().isNotEmpty()){
                            searchViewModel.searchData(s.toString())

                            searchViewModel.searchUser.observe({lifecycle}, {
                                binding.rvSearch.apply {
                                    searchAdapter = SearchAdapter(it)
                                    this.adapter = searchAdapter
                                    this.layoutManager = LinearLayoutManager(context)
                                    searchAdapter.notifyDataSetChanged()
                                    showProgressBar(false)
                                    goToDetailPage()
                                }
                            })

                        }
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                loadJSON()
                username.clearFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadJSON(){
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        searchViewModel.data.observe({lifecycle}, {
            binding.rvSearch.apply {
                searchAdapter = SearchAdapter(it)
                this.adapter = searchAdapter
                this.layoutManager = LinearLayoutManager(context)
                searchAdapter.notifyDataSetChanged()
                showProgressBar(false)
                goToDetailPage()
            }
        })

    }

    private fun showProgressBar(state: Boolean){
        if(state){
            binding.progressBarAccount.visibility = View.VISIBLE
        }else{
            binding.progressBarAccount.visibility = View.GONE
        }
    }

    private fun goToDetailPage(){
        searchAdapter.setOnItemClickCallback(object : IOnItemClickCallback{
            override fun onItemClicked(data: ResultItemsSearch) {
                val bundle = Bundle().apply {
                    putParcelable("detail", data)
                }
                findNavController().navigate(
                    R.id.action_searchFragment_to_detailActivity,
                    bundle
                )
            }
        })
    }
}