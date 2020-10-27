package com.pocketmemo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pocketmemo.repository.RepositoryMemo

@Database(entities = [Memo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun memoDao(): MemoDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java, "app_memo.db").build()
            }
//        fun getInstance(): AppDatabase? {
//            instance
//            if (instance == null) {
//                synchronized(AppDatabase::class) {
//                    instance = Room.databaseBuilder(context.applicationContext,
//                        AppDatabase::class.java, "app_memo.db").build()
//                }
//            }
//            return instance
//        }
    }
}