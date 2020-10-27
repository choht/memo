package com.pocketmemo.adapter.viewholder

import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pocketmemo.data.Memo
import com.pocketmemo.databinding.ItemMemoBinding

class ViewHolderMemo(private val binding: ItemMemoBinding) : RecyclerView.ViewHolder(binding.root) {
    private val tag = ViewHolderMemo::class.java.simpleName

    fun bind(memo: Memo) {
        Log.d(tag, "Memo : $memo")
        binding.tvDesc.text = memo.body
        binding.tvDate.text = long2Time(memo.time)
        itemView.tag = memo
    }

    private fun long2Time(time: Long): String {
        return DateUtils.formatDateTime(itemView.context, time, DateUtils.FORMAT_SHOW_YEAR)
    }

    companion object {
        fun create(parent: ViewGroup): ViewHolderMemo {
            return ViewHolderMemo(ItemMemoBinding
                .bind(LayoutInflater.from(parent.context)
                    .inflate(com.pocketmemo.R.layout.item_memo, parent, false)))
        }
    }
}