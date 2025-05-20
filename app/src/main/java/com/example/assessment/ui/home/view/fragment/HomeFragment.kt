package com.example.assessment.ui.home.view.fragment

import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.assessment.R
import com.example.assessment.databinding.FragmentHomeBinding
import com.example.assessment.ui.home.viewmodel.HomeViewModel
import com.example.assessment.ui.orderdetail.viewmodel.OrderDetailViewModel
import com.example.assessment.ui.util.ApiState
import com.example.assessment.ui.util.isNetworkAvailable
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.time.Duration

@AndroidEntryPoint
class HomeFragment : Fragment(), TextWatcher {
    private lateinit var binding: FragmentHomeBinding
private val viewModel : OrderDetailViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.viewModel=homeViewModel
        binding.lifecycleOwner=this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerData()
       binding.fetchBtn.setOnClickListener {
           fetchOrderDetails()
       }

        binding.editTxt.addTextChangedListener(this)
    }


    private fun fetchOrderDetails() {

        if (homeViewModel.tableIdValue.isEmpty()){
            binding.textField.error="TableId Cannot be Empty"

        }else{
            if (isNetworkAvailable(requireContext())) {
                viewModel.getOrderDetails()
            } else {
                Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    private fun observerData(){
        lifecycleScope.launch {
            viewModel.orderDetail.collect {
                when(it){
                    is ApiState.Loading->{
                        binding.progressBar.visibility= View.VISIBLE
                    }
                    is ApiState.Success->{
                        binding.progressBar.visibility= View.GONE
                        findNavController().navigate(R.id.action_homeFragment_to_orderDetailFragment)
                    }
                    is ApiState.Failure->{
                        binding.progressBar.visibility= View.GONE
                        Log.d("APiFailure", it.toString())
                        Snackbar.make(requireView(), it.error, Snackbar.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }

    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {

    }

    override fun onTextChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) {

    }

    override fun afterTextChanged(s: Editable?) {
        val text = s.toString()
        if (!text.isEmpty()){
            binding.textField.error=null
        }
    }


}