package com.example.assessment.data.models

import com.google.gson.annotations.SerializedName

data class ItemDetails(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?
    // ... other item fields
)
