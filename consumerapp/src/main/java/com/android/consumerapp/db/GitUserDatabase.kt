package  com.android.consumerapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.consumerapp.model.ResultItem


@Database(
     entities = [ResultItem::class],
     version = 2
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
            Room.databaseBuilder(context.applicationContext, GitUserDatabase::class.java, "git_db.db")
                .build()
    }
}
