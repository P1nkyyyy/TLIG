package com.example.seminar_1.features.home.data.network

import com.example.seminar_1.features.home.data.model.FeastModel
import retrofit2.http.GET

interface FeastApiService {
    @GET("today")
    suspend fun getTodayFeast(): FeastModel
}