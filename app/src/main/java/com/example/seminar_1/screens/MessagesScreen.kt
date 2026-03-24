package com.example.seminar_1.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.seminar_1.components.messages.MessagesBottomModal
import com.example.seminar_1.components.messages.MessagesSettingsMenu
import com.example.seminar_1.components.messages.MessageReaderContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen() {
    var showSheet by remember { mutableStateOf(false) }

    var textSize by remember { mutableIntStateOf(18) }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (showSheet)  MessagesBottomModal(
            onDismissRequest = {
                scope.launch {
                    sheetState.hide()
                    showSheet = false
                }
            },
            textSize = textSize,
            onTextSize = { textSize = it }
        )

        MessagesSettingsMenu(handleTextEdit = { showSheet = true }, handleSearch = {/* TODO: Implement search */ })

        MessageReaderContent()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MessagesScreenPreview() {
    MessagesScreen()
}