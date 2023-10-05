package poyan.poyan.insta.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import poyan.poyan.insta.data.model.Post
import poyan.poyan.insta.data.model.PostComments

@Database(entities = [Post::class, PostComments::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun postCommentsDao(): PostCommentsDao

    companion object {
        private const val DB_NAME = "puyann"
        private var instance: DataBase? = null
        @Synchronized
        fun getInstance(context: Context): DataBase? {
            if (instance == null) {
                instance = databaseBuilder<DataBase>(
                    context, DataBase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}