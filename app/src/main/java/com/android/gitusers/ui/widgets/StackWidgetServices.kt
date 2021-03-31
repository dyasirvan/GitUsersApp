package com.android.gitusers.ui.widgets

import android.content.Intent
import android.widget.RemoteViewsService

class StackWidgetServices : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return StackRemoteViewsFactory(this.applicationContext)
    }
}