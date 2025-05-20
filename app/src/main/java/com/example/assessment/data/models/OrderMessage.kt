package com.example.assessment.data.models

import com.google.gson.annotations.SerializedName

data class OrderMessage(
    @SerializedName("order") val order: Order,
    @SerializedName("item") val items: List<OrderItem>,
    @SerializedName("void") val voidItems: List<VoidItem>?,
    @SerializedName("qrCodeUrl") val qrCodeUrl: String?
)
