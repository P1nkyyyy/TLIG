package com.example.seminar_1.features.home.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FeastModel(
    val date: String,
    val season: String,
    val season_week: Int,
    val celebrations: List<FeastCelebrationsModel>,
    val weekday: String,
)