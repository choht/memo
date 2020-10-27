package com.pocketmemo.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.pocketmemo.adapter.viewholder.ViewHolderMemo
import com.pocketmemo.data.Memo

class AdapterMemo : PagedListAdapter<Memo, ViewHolderMemo>(DIFF_CALLBACK), View.OnClickListener {
    private val tag = AdapterMemo::class.java.simpleName

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Memo>() {

                override fun areItemsTheSame(oldItem: Memo, newItem: Memo): Boolean {
                    Log.d("diff", "Diff Item, old : [$oldItem] - new : [$newItem]")
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Memo, newItem: Memo): Boolean {
                    Log.d("diff", "Diff Contents, old : [$oldItem] - new : [$newItem]")
                    return oldItem.body == newItem.body && oldItem.time == newItem.time
                }
            }
    }

    private var mOnMemoListener: OnMemoListener? = null

    override fun onBindViewHolder(holder: ViewHolderMemo, position: Int) {
        Log.d(tag, "Position : $position")
        holder.bind(getItem(position) ?: Memo.empty())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMemo {
        val v = ViewHolderMemo.create(parent)
        v.itemView.setOnClickListener(this)
        return v
    }

    fun setOnMemoListener(listener: OnMemoListener) {
        mOnMemoListener = listener
    }

    interface OnMemoListener {

        fun onMemo(memo: Memo)

    }

    override fun onClick(v: View?) {
        if (v?.tag is Memo)
            mOnMemoListener?.onMemo(v.tag as Memo)
    }
}