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
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assessment.R
import com.example.assessment.data.models.Member
import com.example.assessment.data.models.OrderItem
import com.example.assessment.databinding.FragmentOrderDetailBinding
import com.example.assessment.ui.adapter.OderRecyclerViewAdapter
import com.example.assessment.ui.orderdetail.viewmodel.OrderDetailViewModel
import com.example.assessment.ui.orderdetail.viewmodel.PaymentViewModel
import com.example.assessment.ui.util.ApiState
import com.example.assessment.ui.util.RecyclerViewType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderDetailFragment : Fragment() {
    private lateinit var binding: FragmentOrderDetailBinding
    private val viewModel : OrderDetailViewModel by activityViewModels()
    private val paymentViewModel: PaymentViewModel by viewModels()



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
        observerToolBar()


    }

    private fun observeRadioButtons() {
        binding.splitMethodGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioEvenly -> {
                    setEvenlyRecyclerView()
                }
                R.id.radioByItem -> {
                    setItemRecyclerView()
                }
                R.id.radioByAmount -> {
                    setAmountRecyclerView()
                }
                R.id.radioByShares -> {
                    setShareRecyclerView()
                }
                R.id.radioByPercentage -> {
                    setPercentageRecyclerView()
                }
            }
        }
    }

    private fun setAmountRecyclerView() {
        binding.amountRv.visibility= View.VISIBLE
        val memberCount = viewModel.numberOfMembers.value!!.toInt()
        val totalAmount = viewModel.totalAmount.value!!.toDouble()
        val memberList = createMemberList(memberCount)
        paymentViewModel.setValueForCalculation(memberList,totalAmount)
        val oderItemAdapter = OderRecyclerViewAdapter<Member>(itemLayoutId = R.layout.split_by_amount, memberList = memberList, viewType = RecyclerViewType.AMOUNT, lifecycleOwner = viewLifecycleOwner, viewModel = paymentViewModel)
        binding.amountRv.adapter=oderItemAdapter
        binding.amountRv.layoutManager= LinearLayoutManager(requireContext())

    }

    private fun setItemRecyclerView() {
        binding.amountRv.visibility= View.VISIBLE
        val memberCount = viewModel.numberOfMembers.value!!.toInt()
        val totalAmount = viewModel.totalAmount.value!!.toDouble()
        val memberList = createMemberList(memberCount)
        val oderItems = viewModel.oderItems.value
        paymentViewModel.setValueForCalculation(memberList,totalAmount)
        val oderItemAdapter = OderRecyclerViewAdapter<OrderItem>(itemLayoutId = R.layout.split_by_item_rv, items = oderItems, viewType = RecyclerViewType.ITEM, lifecycleOwner = viewLifecycleOwner, viewModel = paymentViewModel)
        binding.amountRv.adapter=oderItemAdapter
        binding.amountRv.layoutManager= LinearLayoutManager(requireContext())

    }

    private fun setEvenlyRecyclerView() {
        binding.amountRv.visibility= View.VISIBLE
        val memberCount = viewModel.numberOfMembers.value!!.toInt()
        val totalAmount = viewModel.totalAmount.value!!.toDouble()
        val memberList = createMemberList(memberCount)
        paymentViewModel.setValueForCalculation(memberList,totalAmount)
        paymentViewModel.calculateSplitEvently()
        val oderItemAdapter = OderRecyclerViewAdapter<Member>(itemLayoutId = R.layout.split_by_evenly_rv, memberList = memberList, viewType = RecyclerViewType.EVENLY, lifecycleOwner = viewLifecycleOwner, viewModel = paymentViewModel)
        binding.amountRv.adapter=oderItemAdapter
        binding.amountRv.layoutManager= LinearLayoutManager(requireContext())

    }

    private fun setShareRecyclerView() {
        binding.amountRv.visibility= View.VISIBLE
        val memberCount = viewModel.numberOfMembers.value!!.toInt()
        val totalAmount = viewModel.totalAmount.value!!.toDouble()
        val memberList = createMemberList(memberCount)
        paymentViewModel.setValueForCalculation(memberList,totalAmount)
        paymentViewModel.iniSplitByShareInitialValue()
        val oderItemAdapter = OderRecyclerViewAdapter<Member>(itemLayoutId = R.layout.split_by_share_rv, memberList = memberList, viewType = RecyclerViewType.SHARE, lifecycleOwner = viewLifecycleOwner, viewModel = paymentViewModel)
        binding.amountRv.adapter=oderItemAdapter
        binding.amountRv.layoutManager= LinearLayoutManager(requireContext())

    }

    private fun setPercentageRecyclerView() {
        binding.amountRv.visibility= View.VISIBLE
        val memberCount = viewModel.numberOfMembers.value!!.toInt()
        val totalAmount = viewModel.totalAmount.value!!.toDouble()
        val memberList = createMemberList(memberCount)
        paymentViewModel.setValueForCalculation(memberList,totalAmount)
        val oderItemAdapter = OderRecyclerViewAdapter<Member>(itemLayoutId = R.layout.split_by_percentage, memberList = memberList, viewType = RecyclerViewType.PERCENTAGE, lifecycleOwner = viewLifecycleOwner, viewModel = paymentViewModel)
        binding.amountRv.adapter=oderItemAdapter
        binding.amountRv.layoutManager= LinearLayoutManager(requireContext())
    }

    private fun createMemberList(memberCount: Int): ArrayList<Member> {
        val memberList = ArrayList<Member>()
        for (i in 0 .. memberCount){
            memberList.add(Member("Member${i+1}"))
        }
        return  memberList
    }


    private fun observeData() {
        lifecycleScope.launch {
            viewModel.orderDetail.collect {
                when (it) {
                    is ApiState.Loading -> {

                    }

                    is ApiState.Success -> {
                        if (it.data.message.isEmpty()){
                            binding.nodataTxt.visibility= View.VISIBLE
                            binding.parentLinearLayout.visibility= View.GONE

                        }else{
                            binding.order=it.data.message[0].order
                            binding.nodataTxt.visibility= View.GONE
                            binding.parentLinearLayout.visibility= View.VISIBLE
                            val data = it.data.message[0]
                            val oderItemAdapter = OderRecyclerViewAdapter<OrderItem>(itemLayoutId = R.layout.items_rv, items = data.items, viewType = RecyclerViewType.ITEM, lifecycleOwner = viewLifecycleOwner)
                            binding.itemRv.adapter=oderItemAdapter
                            binding.itemRv.layoutManager= LinearLayoutManager(requireContext())
                        }





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
            findNavController().popBackStack()
        }
    }


    private fun observerToolBar(){
        binding.materialToolbar.setNavigationOnClickListener {
            viewModel.clearData()
            findNavController().popBackStack()
        }
    }







}