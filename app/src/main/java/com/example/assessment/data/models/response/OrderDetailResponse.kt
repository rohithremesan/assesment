package com.example.assessment.data.models.response

import com.example.assessment.data.models.OrderItem
import com.example.assessment.data.models.OrderMessage
import com.google.gson.annotations.SerializedName

data class OrderDetailResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: List<OrderMessage>,
    @SerializedName("response_code") val responseCode: Int
)