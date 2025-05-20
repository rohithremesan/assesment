package com.example.assessment.ui.orderdetail.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableInt
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assessment.R
import com.example.assessment.databinding.FragmentOrderDetailBinding
import com.example.assessment.ui.adapter.OderRecyclerViewAdapter
import com.example.assessment.ui.orderdetail.viewmodel.OrderDetailViewModel
import com.example.assessment.ui.util.ApiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderDetailFragment : Fragment() {
    private lateinit var binding: FragmentOrderDetailBinding
    private val viewModel : OrderDetailViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderDetailBinding.inflate(layoutInflater)
        binding.lifecycleOwner=this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        overrideBackPress()
        observeRadioButtons()


    }

    private fun observeRadioButtons() {
        binding.splitMethodGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioEvenly -> {

                }
                R.id.radioByItem -> {

                }
                R.id.radioByAmount -> {

                }
                R.id.radioByShares -> {

                }
            }
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.orderDetail.collect {
                when (it) {
                    is ApiState.Loading -> {

                    }

                    is ApiState.Success -> {
                        val data = it.data.message[0]
                        val oderItemAdapter = OderRecyclerViewAdapter(data.items,R.layout.items_rv)
                        viewModel.orderData=data.order

                        binding.itemRv.adapter=oderItemAdapter
                        binding.itemRv.layoutManager= LinearLayoutManager(requireContext())




                    }

                    is ApiState.Failure -> {

                    }

                    else -> {}
                }
            }
        }
    }

    private fun overrideBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.clearData()
            findNavController().navigate(R.id.action_orderDetailFragment_to_homeFragment)
        }
    }




}