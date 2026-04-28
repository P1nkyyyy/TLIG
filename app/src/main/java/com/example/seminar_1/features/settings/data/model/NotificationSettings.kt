package com.example.seminar_1.features.settings.data.model

data class NotificationSettings(
    val isEnabled: Boolean,
    val hour: Int,
    val minute: Int,
    val toggle: (Boolean, onPermissionRequired: () -> Unit) -> Unit,
    val updateTime: (Int, Int) -> Unit
)
