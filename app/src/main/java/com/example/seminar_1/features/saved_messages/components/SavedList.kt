package com.example.seminar_1.features.saved_messages.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.seminar_1.features.messages.data.model.MessageModel

@Composable
fun SavedList(
    messages: List<MessageModel>,
    navController: NavController,
    onSwipeToDelete: (id: Int) -> Unit,
) {
    var selectedIds by remember { mutableStateOf(setOf<Int>()) }
    val isMultiSelectMode = selectedIds.isNotEmpty()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = messages,
            key = { it.id }
        ) { message ->
            val isSelected = selectedIds.contains(message.id)

            MessageCard(
                message = message,
                isSelectionMode = isMultiSelectMode,
                isSelected = isSelected,
                onClick = {
                    if (isMultiSelectMode) {
                        selectedIds = if (isSelected) {
                            selectedIds - message.id
                        } else {
                            selectedIds + message.id
                        }
                    } else {
                        navController.navigate("messages?messageId=${message.id}&scrollToLast=true")
                    }
                },
                onLongClick = {
                    if (!isMultiSelectMode) {
                        selectedIds = setOf(message.id)
                    }
                },
                onSwipeToDelete = {
                    onSwipeToDelete(message.id)
                    selectedIds = selectedIds - message.id
                }
            )
        }
    }
}