package com.example.assessment.data.models

import com.google.gson.annotations.SerializedName

data class OrderItem(
    @SerializedName("id") val id: Int,
    @SerializedName("item_name") val itemName: String,
    @SerializedName("qty") val quantity: Int,
    @SerializedName("price") val price: String,
    @SerializedName("total") val total: String,
    var assignedTo: String
)