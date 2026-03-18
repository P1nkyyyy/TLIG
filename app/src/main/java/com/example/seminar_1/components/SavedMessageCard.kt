package com.example.seminar_1.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SavedMessageCard(title: String, description: String, date: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp)

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(date)
            Text(title, fontSize = 18.sp)
            Text(description)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SavedMessageCardPreview() {
    SavedMessageCard("Můj Svatý Duch je Životodárce", "Můj Svatý Duch je vyléván na lidstvo...", "15. dubna 1999")
}