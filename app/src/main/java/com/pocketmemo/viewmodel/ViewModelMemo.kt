package com.pocketmemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.pocketmemo.data.Memo
import com.pocketmemo.repository.RepositoryMemo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ViewModelMemo(private val repoMemo: RepositoryMemo) : ViewModel() {
    companion object {
        const val DEFAULT_PAGE_SIZE : Int = 50
    }

    val memo = MutableLiveData<Memo?>()
    val memoList: LiveData<PagedList<Memo>> = repoMemo.listMemo().toLiveData(DEFAULT_PAGE_SIZE)

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Main + viewModelJob)

    fun selected(memo: Memo?) {
        this.memo.value = memo?.copy(id = memo.id, time = memo.time, body = memo.body)
    }

    fun deleteMemo(memo: Memo) {
        viewModelScope.launch { repoMemo.delete(memo) }
    }

    fun insertMemo(memo: Memo) {
        viewModelScope.launch { repoMemo.insert(memo) }
    }

    fun updateMemo(memo: Memo) {
        viewModelScope.launch { repoMemo.update(memo) }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val repoMemo: RepositoryMemo) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ViewModelMemo(repoMemo) as T
        }

    }
}