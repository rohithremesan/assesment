package com.example.assessment.ui.orderdetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assessment.data.models.Order
import com.example.assessment.data.models.OrderItem
import com.example.assessment.data.models.OrderMessage
import com.example.assessment.data.models.response.OrderDetailResponse
import com.example.assessment.domain.repositories.OrderDetailRepository
import com.example.assessment.ui.util.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor( private val repository: OrderDetailRepository): ViewModel() {

    private val _orderDetail = MutableStateFlow<ApiState>(ApiState.Nothing)
    val orderDetail: StateFlow<ApiState> = _orderDetail
     var modifiedItemList: List<OrderItem> = listOf()
    var orderData:Order?=null

    fun getOrderDetails(){

        viewModelScope.launch {
            repository.getOrderDetails(86).collect {
                _orderDetail.value=it
            }


        }

    }


    fun clearData(){
        _orderDetail.value= ApiState.Nothing
    }




}