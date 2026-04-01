package com.example.seminar_1.screens.saved_messages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.seminar_1.data.model.MessageModel
import com.example.seminar_1.ui.theme.Seminar1Theme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.seminar_1.screens.saved_messages.components.MessageCard
import com.example.seminar_1.utils.textParser

@Composable
fun SavedMessagesScreen(
    viewModel: SavedMessagesViewModel = viewModel(factory = SavedMessagesViewModel.Factory)
) {
    val messages by viewModel.messages.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyColumn{
            items(messages) { message: MessageModel ->
                MessageCard(
                    message.title,
                    textParser(message.content),
                    message.date
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SavedMessagesScreenPreview() {
    Seminar1Theme {
        SavedMessagesScreen()
    }
}