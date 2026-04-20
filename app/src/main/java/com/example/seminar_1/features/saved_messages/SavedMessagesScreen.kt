package com.example.seminar_1.features.saved_messages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.seminar_1.features.saved_messages.components.MessageCard
import com.example.seminar_1.ui.theme.Seminar1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedMessagesScreen(
    navController: NavController,
    viewModel: SavedMessagesViewModel = viewModel(factory = SavedMessagesViewModel.Factory)
) {
    val messages by viewModel.messages.collectAsState()
    var selectedIds by remember { mutableStateOf(setOf<Int>()) }

    val isMultiSelectMode = selectedIds.isNotEmpty()

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = isMultiSelectMode,
                onClick = { selectedIds = emptySet() }
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Tady by mohl být tvůj Contextual Bar (ten z předchozí verze)
            // AnimatedVisibility(visible = isMultiSelectMode) { ... }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = messages,
                    key = { it.id } // Klíč je kritický pro SwipeToDismiss
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
                                // Otevře detail a odroluje na poslední pozici
                                navController.navigate("messages?messageId=${message.id}&scrollToLast=true")
                            }
                        },
                        onLongClick = {
                            if (!isMultiSelectMode) {
                                selectedIds = setOf(message.id)
                            }
                        },
                        onSwipeToDelete = {
                            viewModel.unarchiveMessages(listOf(message.id))
                            selectedIds = selectedIds - message.id
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SavedMessagesScreenPreview() {
    val navController = rememberNavController()
    Seminar1Theme {
        SavedMessagesScreen(navController)
    }
}