package com.example.assessment.ui.orderdetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _numberOfMembers = MutableLiveData<Int>()
    val numberOfMembers: LiveData<Int> = _numberOfMembers
    private val _totalAmount = MutableLiveData<Double>()
    val totalAmount: LiveData<Double> = _totalAmount
    private val _oderItems = MutableLiveData<ArrayList<OrderItem>>()
    val oderItems: LiveData<ArrayList<OrderItem>> = _oderItems


    fun getOrderDetails(tableId: Int){

        viewModelScope.launch {
            repository.getOrderDetails(tableId).collect {
                _orderDetail.value=it
                if (it is ApiState.Success){
                    if (!it.data.message.isEmpty()){
                        setValues(it.data.message[0])
                    }
                }
            }


        }

    }

    fun setValues(data: OrderMessage){
        _totalAmount.value=data.order.total.toDouble()
        _numberOfMembers.value=data.order.familyCount!!.toInt()
        _oderItems.value=data.items

    }

    fun clearData(){
        _orderDetail.value= ApiState.Nothing
    }




}