package com.android.gitusers.ui.widgets

import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.util.Log
import com.android.gitusers.R

class UpdateWidgetService: JobService() {
    companion object{
        private val TAG = javaClass.simpleName
        const val JOB_ID = 12
    }
    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(TAG, "onStartJob: ")
        params?.let { handleUpdateAppWidgets(it) }
        return true
    }

    private fun stopJobUpdate() {
        val stJob = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        stJob.cancel(JOB_ID)
        Log.d(TAG, "stopJobUpdate: ")
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG, "onStopJob: ")
        return false
    }

    private fun handleUpdateAppWidgets(parameters: JobParameters) {
        val context = applicationContext
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val thisWidget = ComponentName(context, GitUsersAppWidget::class.java)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view)
        jobFinished(parameters, false)
        stopJobUpdate()
    }
}