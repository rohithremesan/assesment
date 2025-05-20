package com.example.assessment.data.services

import com.example.assessment.data.models.request.TableRequest
import com.example.assessment.data.models.response.OrderDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface OderService{
    @POST("getOrderByTableId")
   suspend fun getOrderDetails(@Body request: TableRequest): Response<OrderDetailResponse>
}