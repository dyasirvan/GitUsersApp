package com.android.gitusers.ui.widgets

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
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

class StackRemoteViewsFactory(val context: Context) : RemoteViewsService.RemoteViewsFactory {
    companion object{
        private var mWidgetItems : List<ResultItemsSearch> = ArrayList()
        private lateinit var favoriteAdapter: FavoriteAdapter
        private lateinit var favoriteViewModel: FavoriteViewModel
    }
    override fun onCreate() {
    }

    override fun onDataSetChanged() {
        val gitUserRepository = GitUserRepository(context.let { GitUserDatabase.invoke(it) })

        favoriteAdapter = FavoriteAdapter(ArrayList<ResultItemsSearch>())
        favoriteViewModel = ViewModelProviders.of(context as FragmentActivity).get(FavoriteViewModel::class.java)
        favoriteViewModel.getDataUser(gitUserRepository).observe({ context.lifecycle }, {
            favoriteAdapter = FavoriteAdapter(it)
            favoriteAdapter.notifyDataSetChanged()
        })
    }

    override fun onDestroy() {
    }

    override fun getCount(): Int {
        return mWidgetItems.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val resultItemsSearch = mWidgetItems[position]
        val rv = RemoteViews(context.packageName, R.layout.git_users_widget_item)
        val img: ImageView? = null
        if (img != null) {
            Glide.with(context)
                    .load(resultItemsSearch.avatar_url)
                    .into(img)
            img.buildDrawingCache()
            val bmap = img.drawingCache
            rv.setImageViewBitmap(R.id.image_poster_widget, bmap)
            rv.setTextViewText(R.id.text_title_widget, resultItemsSearch.login)
        }
        val extras = Bundle()
        extras.putInt(GitUsersAppWidget.EXTRA_ITEM, position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.image_poster_widget, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun hasStableIds(): Boolean {
        return false
    }
}