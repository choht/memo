package com.pocketmemo.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.pocketmemo.R
import com.pocketmemo.adapter.AdapterMemo
import com.pocketmemo.data.Memo
import com.pocketmemo.databinding.FragmentHomeBinding
import com.pocketmemo.viewmodel.ViewModelMemo

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MemoListFragment : Fragment(), AdapterMemo.OnMemoListener {

    private lateinit var binding: FragmentHomeBinding
    private val mViewModel: ViewModelMemo by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //(activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_home_label)

        //val model: ViewModelMemo by activityViewModels()
        val adapter = AdapterMemo()
        adapter.setOnMemoListener(this)
        binding.memoList.adapter = adapter
        mViewModel.memoList.observe(viewLifecycleOwner, { pagedList ->
            run {
                Log.d("List", "List Count : " + pagedList.size + " [ " + pagedList[0] + " ]")
                adapter.submitList(pagedList)
            }
        })
    }

    override fun onMemo(memo: Memo) {
        Log.d("List", "Selected memo : $memo")
        mViewModel.selected(memo)
        findNavController().navigate(MemoEditFragmentDirections.actionGlobalEdit())
    }
}