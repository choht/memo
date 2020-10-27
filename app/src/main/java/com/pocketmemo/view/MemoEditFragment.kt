package com.pocketmemo.view

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.transition.Slide
import com.google.android.material.transition.MaterialContainerTransform
import com.pocketmemo.R
import com.pocketmemo.data.Memo
import com.pocketmemo.databinding.FragmentDetailBinding
import com.pocketmemo.viewmodel.ViewModelMemo
import kotlinx.android.synthetic.main.fragment_detail.*
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MemoEditFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: ViewModelMemo by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.btnBack.setOnClickListener {
            navigateToHome()
        }

        enterTransition = MaterialContainerTransform().apply {
            startView = requireActivity().findViewById(R.id.fab)
            endView = memo_detail_view
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            scrimColor = Color.TRANSPARENT
        }
        returnTransition = Slide().apply {
            duration = resources.getInteger(R.integer.motion_duration_medium).toLong()
            addTarget(R.id.memo_detail_view)
        }

        viewModel.memo.observe(viewLifecycleOwner, { memo ->
            Log.d("Edit", "memo : $memo")
            if (memo != null) initView(memo)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        saveMemo()
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    private fun initView(memo: Memo) {
        binding.editContent.text = Editable.Factory.getInstance().newEditable(memo.body)
    }

    private fun isEditMode(): Boolean {
        return viewModel.memo.value != null
    }

    private fun navigateToHome() {
        findNavController().navigateUp()
    }

    private fun saveMemo() {
        if (isEditMode()) {
            val memo = viewModel.memo.value
            if (binding.editContent.text.isNotEmpty()) {
                if (binding.editContent.text.toString() != memo!!.body) {
                    memo.body = binding.editContent.text.toString()
                    memo.time = Date().time
                    viewModel.updateMemo(memo)
                }
            }
            else {
                viewModel.deleteMemo(memo!!)
            }
        }
        else {
            if (binding.editContent.text.isNotEmpty()) {
                viewModel.insertMemo(Memo.newInstance(binding.editContent.text.toString()))
            }
        }
    }
}