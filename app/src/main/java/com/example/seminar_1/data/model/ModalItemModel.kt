package com.example.seminar_1.data.model

import androidx.compose.runtime.Composable

data class ModalItemModel(
    val text: String,
    val component: @Composable () -> Unit,
)