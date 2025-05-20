package com.example.assessment.di

import com.example.assessment.data.datasource.OderDetailDataSource
import com.example.assessment.data.services.OderService
import com.example.assessment.domain.repositories.OrderDetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api-new.fmb.eposapi.co.uk")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideOderDetails(retrofit: Retrofit): OderService{
        return retrofit.create(OderService::class.java)
    }

    @Provides
    @Singleton
    fun provideOrderDetailRepository(
        apiService: OderService
    ): OrderDetailRepository = OderDetailDataSource(apiService)


}