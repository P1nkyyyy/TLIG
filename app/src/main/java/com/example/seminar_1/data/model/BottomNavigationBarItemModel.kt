package com.example.seminar_1.data.model

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationBarItemModel(
    val routeName: String,
    val icon: ImageVector,
    val stringResource: Int,
    val contentDescription: String? = null
)