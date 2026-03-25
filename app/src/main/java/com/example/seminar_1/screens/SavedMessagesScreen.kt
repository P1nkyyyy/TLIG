package com.example.seminar_1.screens

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
import com.example.seminar_1.AppDatabase
import com.example.seminar_1.data_classes.MessageType
import com.example.seminar_1.ui.theme.Seminar1Theme
import kotlin.collections.emptyList
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.seminar_1.components.saved.MessageCard
import com.example.seminar_1.utils.textParser

@Composable
fun SavedMessagesScreen() {
    val context = LocalContext.current

    val db = AppDatabase.getDatabase(context)
    val dao = db.messagesDao()

    val messages by dao.getAll().collectAsState(emptyList())

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyColumn{
            items(messages) { message: MessageType ->
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