package com.example.assessment.data.models

import com.google.gson.annotations.SerializedName

data class OrderMessage(
    @SerializedName("order") val order: Order,
    @SerializedName("item") val items: ArrayList<OrderItem>,
    @SerializedName("void") val voidItems: ArrayList<VoidItem>?,
    @SerializedName("qrCodeUrl") val qrCodeUrl: String?
)
