package com.example.seminar_1.features.saved_messages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.seminar_1.features.saved_messages.components.MessageCard
import com.example.seminar_1.ui.theme.Seminar1Theme
import com.example.seminar_1.utils.removeNoteParser
import com.example.seminar_1.utils.textParser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedMessagesScreen(
    navController: NavController,
    viewModel: SavedMessagesViewModel = viewModel(factory = SavedMessagesViewModel.Factory)
) {
    val messages by viewModel.messages.collectAsState()

    // Seznam vybraných ID
    var selectedIds by remember { mutableStateOf(setOf<Int>()) }
    val isMultiSelectMode = selectedIds.isNotEmpty()

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = isMultiSelectMode,
                onClick = { selectedIds = emptySet() }
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Contextual Bar pro hromadné akce
            AnimatedVisibility(
                visible = isMultiSelectMode,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    tonalElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .statusBarsPadding(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { selectedIds = emptySet() }) {
                                Icon(Icons.Default.Close, contentDescription = "Zrušit")
                            }
                            Text(
                                text = "Vybráno: ${selectedIds.size}",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }

                        Row {
                            IconButton(
                                onClick = {
                                    viewModel.unarchiveMessages(selectedIds.toList())
                                    selectedIds = emptySet()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Odstranit vybrané",
                                    tint = Color.Red
                                )
                            }
                            IconButton(
                                onClick = {}
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Sdílet vybrané",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = messages,
                    key = { it.id }
                ) { message ->
                    val isSelected = selectedIds.contains(message.id)

                    MessageCard(
                        title = message.title,
                        description = textParser(message.content),
                        date = removeNoteParser(message.date),
                        isSelected = isSelected,
                        onClick = {
                            if (isMultiSelectMode) {
                                selectedIds = if (isSelected) {
                                    selectedIds - message.id
                                } else {
                                    selectedIds + message.id
                                }
                            } else {
                                // Normální klik otevře detail
                                navController.navigate("messages?messageId=${message.id}")
                            }
                        },
                        onLongClick = {
                            if (!isMultiSelectMode) {
                                selectedIds = setOf(message.id)
                            }
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