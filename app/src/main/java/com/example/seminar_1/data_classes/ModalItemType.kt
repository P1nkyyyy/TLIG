package com.example.seminar_1.data_classes

import androidx.compose.runtime.Composable

data class ModalItemType(
    val text: String,
    val component: @Composable () -> Unit,
)