package com.android.gitusers.ui.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.android.gitusers.R

/**
 * Implementation of App Widget functionality.
 */
class GitUsersAppWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
    }
    companion object{
        const val TOAST_ACTION = "com.android.gitusers.TOAST_ACTION"
        const val EXTRA_ITEM = "com.android.gitusers.EXTRA_ITEM"
    }
}

internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
    // set intent for widget service that will create the views
    val intent = Intent(context, StackWidgetServices::class.java)
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
    intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
    val views = RemoteViews(context.packageName, R.layout.git_users_app_widget)
    views.setRemoteAdapter(R.id.stack_view, intent)
    views.setEmptyView(R.id.stack_view, R.id.empty_view)
    val toastIntent = Intent(context, GitUsersAppWidget::class.java)
    toastIntent.action = GitUsersAppWidget.TOAST_ACTION
    toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
    intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
    val toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)
    appWidgetManager.updateAppWidget(appWidgetId, views)
}
