package com.example.assessment.data.models

import com.google.gson.annotations.SerializedName

data class VoidItem(
    @SerializedName("item_name") val itemName: String,
    @SerializedName("price") val price: Double,
    @SerializedName("reason") val reason: String
)
