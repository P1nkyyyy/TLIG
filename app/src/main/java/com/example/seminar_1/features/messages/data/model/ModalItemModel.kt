package com.example.seminar_1.features.messages.data.model

import androidx.compose.runtime.Composable

data class ModalItemModel(
    val text: String,
    val component: @Composable () -> Unit,
)