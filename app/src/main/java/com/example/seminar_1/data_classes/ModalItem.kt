package com.example.seminar_1.data_classes

import androidx.compose.runtime.Composable

data class ModalItem(
    val text: String,
    val component: @Composable () -> Unit,
)