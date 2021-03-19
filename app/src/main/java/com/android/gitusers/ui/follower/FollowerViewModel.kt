package com.android.gitusers.ui.follower

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.gitusers.api.RetrofitInstance
import com.android.gitusers.model.FollowersItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FollowerViewModel: ViewModel() {
    private var TAG: String = "FollowerViewModel"
    private val _data = MutableLiveData<ArrayList<FollowersItem>>()

    val data: LiveData<ArrayList<FollowersItem>>
        get() = _data

    private var job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    fun getFollower(querySearch: String){
        uiScope.launch {
            try {
                val result = RetrofitInstance.api.followerAccount(username = querySearch)

                if(result.isSuccessful){
                    _data.postValue(result.body())
                }else{
                    Log.d(TAG, "getFollower: ${result.errorBody()}")
                }
            }catch (t: Throwable){
                Log.d(TAG, "getFollower: ${t.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}