package com.pocketmemo.data

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface MemoDao {
    @Query("SELECT * FROM table_memo ORDER BY time DESC")
    fun getAll(): DataSource.Factory<Int, Memo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(memo: Memo)

    @Delete
    fun delete(memo: Memo)

    @Update
    fun update(memo: Memo)
}