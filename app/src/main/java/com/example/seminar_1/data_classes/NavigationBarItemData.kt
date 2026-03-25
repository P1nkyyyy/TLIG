package com.example.seminar_1.data_classes

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationBarItemData(
    val routeName: String,
    val icon: ImageVector,
    val stringResource: Int,
    val contentDescription: String? = null
)
