package com.android.gitusers.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.gitusers.api.RetrofitInstance
import com.android.gitusers.model.ResultItemsSearch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {
    private val TAG: String = "SearchViewModel"
    private val _data = MutableLiveData<List<ResultItemsSearch>>()

    val data : LiveData<List<ResultItemsSearch>>
        get() = _data

    private val _searchUser = MutableLiveData<List<ResultItemsSearch>>()
    val searchUser : LiveData<List<ResultItemsSearch>>
        get() = _searchUser

    private val _response = MutableLiveData<String>()
    val response : LiveData<String>
        get() = _response

    private var job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    init {
        _response.value = ""
        initData()
    }

    private fun initData(){
        uiScope.launch {
            try {
                val result = RetrofitInstance.api.searchAccount(username = "dyasirvan")

                if(result.items.isNotEmpty()){
                    _data.value = result.items
                    _response.value = "Berhasil ambil data"
                    Log.d(TAG, "initData: ${_response.value}")
                }else{
                    _response.value = "Data kosong!"
                    Log.d(TAG, "initData: ${_response.value}")
                }
            }catch (t: Throwable){
                Log.d(TAG, "initData: $t")
            }
        }
    }

    fun searchData(querySearcch: String){
        uiScope.launch {
            try {
                val result = RetrofitInstance.api.searchAccount(username = querySearcch)

                if(result.items.isNotEmpty()){
                    _searchUser.value = result.items
                    _response.value = "Data ditemukan"
                    Log.d(TAG, "initData: ${_response.value}")
                }else{
                    _response.value = "Data tidak ditemukan!"
                    Log.d(TAG, "initData: ${_response.value}")
                }
            }catch (t: Throwable){
                Log.d(TAG, "initData: $t")
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}

