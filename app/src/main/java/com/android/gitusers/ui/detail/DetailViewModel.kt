package com.android.gitusers.ui.detail

import android.app.Application
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.gitusers.api.RetrofitInstance
import com.android.gitusers.model.DetailUser
import com.android.gitusers.model.ResultItemsSearch
import com.android.gitusers.repository.GitUserRepository
import com.android.gitusers.ui.widgets.UpdateWidgetService
import com.android.gitusers.ui.widgets.UpdateWidgetService.Companion.JOB_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG: String = "DetailViewModel"
    private val _data = MutableLiveData<DetailUser>()
    private val context = getApplication<Application>().applicationContext
    private val SCHEDULE_PERIOD = 1 * 1000

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
        startJobUpdate()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    private fun startJobUpdate() {
        val mServiceComponent = ComponentName(context, UpdateWidgetService::class.java)
        val builder = JobInfo.Builder(
            JOB_ID,
            mServiceComponent
        )
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setMinimumLatency(SCHEDULE_PERIOD.toLong())
        } else {
            builder.setPeriodic(SCHEDULE_PERIOD.toLong())
        }
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.schedule(builder.build())
    }
}