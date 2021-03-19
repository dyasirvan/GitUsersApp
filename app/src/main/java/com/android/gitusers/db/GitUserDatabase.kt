package com.android.gitusers.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.gitusers.model.ResultItemsSearch


@Database(
     entities = [ResultItemsSearch::class],
     version = 1
)
abstract class GitUserDatabase : RoomDatabase() {
    abstract fun getDatasDao(): GitUserDao

    companion object {
        @Volatile
        private var instance: GitUserDatabase?= null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also{ instance = it}
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, GitUserDatabase::class.java, "git_db.db").build()
    }
}
