package com.example.assessment.ui.util

import com.example.assessment.data.models.response.OrderDetailResponse

sealed class ApiState {

   data class Success(val data : OrderDetailResponse): ApiState()
   data class Failure(val error: String): ApiState()
    object Loading: ApiState()
    object Nothing: ApiState()
}