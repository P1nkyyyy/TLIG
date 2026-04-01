package com.example.seminar_1.screens.messages.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seminar_1.ui.theme.Seminar1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteModal(
    noteText: String?,
    onClose: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onClose
    ) {
        Column(
            modifier = Modifier.padding(bottom = 24.dp, start = 24.dp, end = 24.dp),
        ) {
            Text(
                "Poznámka",
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(noteText ?: "")
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NoteModalPreview() {
    Seminar1Theme {
        Box(modifier = Modifier.padding(16.dp)) {
            NoteModal("Tzn., že si budu vědoma Boží Přítomnosti.", onClose = {})
        }
    }
}