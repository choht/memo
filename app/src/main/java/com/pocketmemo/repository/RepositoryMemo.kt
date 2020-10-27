package com.pocketmemo.repository

import com.pocketmemo.data.Memo
import com.pocketmemo.data.MemoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryMemo private constructor(private val memoDao: MemoDao) {

    fun listMemo() = memoDao.getAll()

    suspend fun delete(memo: Memo) {
        withContext(Dispatchers.IO) {
            memoDao.delete(memo)
        }
    }

    suspend fun insert(memo: Memo) {
        withContext(Dispatchers.IO) {
            memoDao.insert(memo)
        }
    }

    suspend fun update(memo: Memo) {
        withContext(Dispatchers.IO) {
            memoDao.update(memo)
        }
    }

    companion object {
        @Volatile
        private var instance: RepositoryMemo? = null

        fun getInstance(memoDao: MemoDao) =
            instance ?: synchronized(this) {
                instance ?: RepositoryMemo(memoDao).also { instance = it }
            }
    }
}