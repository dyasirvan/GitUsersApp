package com.android.consumerapp

import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.consumerapp.MainActivity.Companion.CONTENT_URI
import com.android.consumerapp.adapter.FavoriteAdapter
import com.android.consumerapp.databinding.ActivityMainBinding
import com.android.consumerapp.helper.MappingHelper
import com.android.consumerapp.model.ResultItem
import com.android.consumerapp.utils.Constants.Companion.TABLE_NAME
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    val uiScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Consumer App"

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.setHasFixedSize(true)
        favoriteAdapter = FavoriteAdapter(this)
        binding.rvFavorite.adapter = favoriteAdapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadNotesAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            savedInstanceState.getParcelableArrayList<ResultItem>(EXTRA_STATE)?.also {
                favoriteAdapter.listResultItem = it
            }
        }

    }

    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressBar.visibility = View.VISIBLE
            val deferredResultItem = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val resultItem = deferredResultItem.await()
//            Log.d("ConsumerApp", "loadNotesAsync: $resultItem")
            binding.progressBar.visibility = View.GONE
            if (resultItem.size > 0) {
                favoriteAdapter.listResultItem = resultItem

            } else {
                favoriteAdapter.listResultItem = ArrayList()
                Toast.makeText(applicationContext, "Data Kosong", Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, favoriteAdapter.listResultItem)
    }

    companion object{
        private const val AUTHORITY = "com.android.gituser"
        val CONTENT_URI: Uri = Uri.Builder().scheme("content")
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

}
