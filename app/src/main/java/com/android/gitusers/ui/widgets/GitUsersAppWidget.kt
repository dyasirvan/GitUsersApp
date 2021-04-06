package com.android.gitusers.ui.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.android.gitusers.MainActivity
import com.android.gitusers.R
import com.android.gitusers.ui.detail.DetailActivity

/**
 * Implementation of App Widget functionality.
 */
class GitUsersAppWidget : AppWidgetProvider() {

    private fun updateAppWidget(
        context: Context, appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        // set intent for widget service that will create the views
        val intent = Intent(context, StackWidgetServices::class.java)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.git_users_app_widget)
        views.setRemoteAdapter(R.id.stack_view, intent)
        views.setEmptyView(R.id.stack_view, R.id.empty_view)

        // Set intent for item click
        val viewIntent = Intent(context, MainActivity::class.java)
        viewIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        val viewPendingIntent =
            PendingIntent.getActivity(context, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        views.setPendingIntentTemplate(R.id.stack_view, viewPendingIntent)

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        updateAllWidget(
            context,
            appWidgetManager,
            appWidgetIds
        )
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    private fun updateAllWidget(
        context: Context?,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            if (context != null) {
                updateAppWidget(
                    context,
                    appWidgetManager,
                    appWidgetId
                )
            }
        }
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view_item)
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
}
