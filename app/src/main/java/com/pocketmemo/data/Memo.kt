package com.pocketmemo.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "table_memo")
data class Memo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "time") var time: Long,
    @ColumnInfo(name = "body") var body: String
) {

    companion object {
        fun empty() : Memo {
            return newInstance("")
        }

        fun newInstance(@NonNull content: String) : Memo {
            return Memo(0, Date().time, content)
        }
    }

    override fun toString(): String {
        return "[$id] : $body ( $time )"
    }
}