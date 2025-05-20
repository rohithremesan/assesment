package com.example.assessment.domain.repositories

import com.example.assessment.data.models.response.OrderDetailResponse
import com.example.assessment.ui.util.ApiState
import kotlinx.coroutines.flow.Flow

interface OrderDetailRepository {

    suspend fun getOrderDetails(tableNo: Int): Flow<ApiState>
}