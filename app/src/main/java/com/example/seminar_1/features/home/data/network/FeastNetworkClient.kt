package com.example.seminar_1.features.home.data.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object FeastNetworkClient {
    private const val BASE_URL = "http://calapi.inadiutorium.cz/api/v0/cs/calendars/czech/"

    val apiService: FeastApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(FeastApiService::class.java)
    }
}