package com.android.gitusers.ui.widgets

import android.content.Context
import android.graphics.Bitmap
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.android.gitusers.R
import com.android.gitusers.adapter.FavoriteAdapter
import com.android.gitusers.db.GitUserDatabase
import com.android.gitusers.model.ResultItemsSearch
import com.android.gitusers.repository.GitUserRepository
import com.android.gitusers.ui.favorite.FavoriteViewModel
import com.bumptech.glide.Glide
import java.util.*
import java.util.concurrent.ExecutionException

class StackRemoteViewsFactory(val context: Context) : RemoteViewsService.RemoteViewsFactory {
    companion object{
        private var mWidgetItems : ArrayList<ResultItemsSearch> = ArrayList()
    }
    override fun onCreate() {
    }

    override fun onDataSetChanged() {
        val gitUserRepository = GitUserRepository(context.let { GitUserDatabase.invoke(it) })

        val identityToken = Binder.clearCallingIdentity()
        mWidgetItems.addAll(gitUserRepository.getNotLiveDataFromDb())
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun onDestroy() {
        mWidgetItems.clear()
    }

    override fun getCount(): Int {
        return mWidgetItems.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val resultItemsSearch = mWidgetItems[position]
        val rv = RemoteViews(context.packageName, R.layout.git_users_widget_item)

        if (mWidgetItems.isNotEmpty()) {
            try {
                val bitmap: Bitmap = Glide.with(context)
                    .asBitmap()
                    .load(resultItemsSearch.avatar_url)
                    .submit(200, 100)
                    .get()
                rv.setImageViewBitmap(R.id.image_poster_widget, bitmap)
            } catch (e: ExecutionException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        rv.setTextViewText(R.id.text_title_widget, resultItemsSearch.login)
        return rv
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }
}