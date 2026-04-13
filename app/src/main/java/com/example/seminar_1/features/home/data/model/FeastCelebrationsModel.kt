package com.example.seminar_1.features.home.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FeastCelebrationsModel(
    val title: String,
    val colour: String,
    val rank: String,
    val rank_num: Double,
)