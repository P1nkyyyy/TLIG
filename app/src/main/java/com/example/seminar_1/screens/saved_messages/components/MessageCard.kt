package com.example.seminar_1.screens.saved_messages.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MessageCard(title: String, description: String, date: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = date,
                fontStyle = FontStyle.Italic
            )
            Text(
                text = title,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 4.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "\"$description\"",
                modifier = Modifier.padding(top = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.SansSerif,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MessageCardPreview() {
    MessageCard(
        "Můj Svatý Duch je Životodárce",
        "Můj Svatý Duch je vyléván na lidstvo v hojnosti, aby vás všechny obnovil a přivedl k plnému poznání Pravdy.",
        "15. dubna 1999"
    )
}