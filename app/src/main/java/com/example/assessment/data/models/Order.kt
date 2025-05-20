package com.example.assessment.data.models

import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("id") val id: Int,
    @SerializedName("total") val total: String,
    @SerializedName("datetime") val datetime: String,
    @SerializedName("isTableOrder") val isTableOrder: Boolean?,
    @SerializedName("familyCount") val familyCount: Int?,
    @SerializedName("tableCount") val tableCount: Int?,
    @SerializedName("remainingTableCount") val remainingTableCount: Int?
)
