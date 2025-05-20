package com.example.assessment.data.datasource

import com.example.assessment.BuildConfig
import com.example.assessment.data.models.request.TableRequest
import com.example.assessment.data.models.response.OrderDetailResponse
import com.example.assessment.data.services.OderService
import com.example.assessment.domain.repositories.OrderDetailRepository
import com.example.assessment.ui.util.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OderDetailDataSource @Inject constructor (
  private val  orderServiceApi:OderService
): OrderDetailRepository {
    override suspend fun getOrderDetails(
        tableNo: Int
    ): Flow<ApiState> =flow {
        try {
            emit(ApiState.Loading)
            val apiResponce = orderServiceApi.getOrderDetails(TableRequest(BuildConfig.REST_API_KEY, tableNo))

            if (apiResponce.isSuccessful && apiResponce.body() != null) {

                emit(ApiState.Success(apiResponce.body()!!))
            }


        } catch (e: Exception) {

            emit(ApiState.Failure(e.toString()))


        }
    }.flowOn(Dispatchers.IO)


}