package com.android.gitusers.ui.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.gitusers.api.RetrofitInstance
import com.android.gitusers.model.FollowingItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FollowingViewModel: ViewModel() {
    private var TAG: String = "FollowingViewModel"
    private val _data = MutableLiveData<ArrayList<FollowingItem>>()

    val data: LiveData<ArrayList<FollowingItem>>
        get() = _data

    private var job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    fun getFollowing(querySearch: String){
        uiScope.launch {
            try {
                val result = RetrofitInstance.api.followingAccount(username = querySearch)

                if(result.isSuccessful){
                    _data.postValue(result.body())
                }else{
                    Log.d(TAG, "getFollowing: ${result.errorBody()}")
                }
            }catch (t: Throwable){
                Log.d(TAG, "getFollowing: ${t.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}