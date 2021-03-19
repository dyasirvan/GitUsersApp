package com.android.gitusers.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.gitusers.api.RetrofitInstance
import com.android.gitusers.model.DetailUser
import com.android.gitusers.model.ResultItemsSearch
import com.android.gitusers.repository.GitUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private val TAG: String = "DetailViewModel"
    private val _data = MutableLiveData<DetailUser>()

    val data: LiveData<DetailUser>
        get() = _data

    private var job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    fun detailDataUser(querySearch: String){
        uiScope.launch {
            try {
                val result = RetrofitInstance.api.detailAccount(username = querySearch)
                if(result.isSuccessful) {
                    _data.postValue(result.body())
                }else{
                    Log.d(TAG, "detailDataUser: $result")
                }
            }catch (t: Throwable){
                Log.d(TAG, "detailDataUser: ${t.message}")
            }
        }
    }

    fun saveData(data: ResultItemsSearch, gitUserRepository: GitUserRepository) = viewModelScope.launch {
        gitUserRepository.insertToDb(data)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}