package com.example.seminar_1.components.messages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun FontSelectContent(
    selectedFont: String,
    onBack: () -> Unit,
    onFontSelected: (String) -> Unit
) {
    val fonts = listOf("Serif", "Sans-Serif", "Monospace", "Roboto", "Times New Roman")
    val scrollState = rememberScrollState()

    Column {
        Box(modifier = Modifier.fillMaxWidth().verticalScroll(scrollState)) {
            IconButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Zpět")
            }
            Text(
                "FONT",
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold
            )
        }

        fonts.forEach { font ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onFontSelected(font) }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = font,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = if (font == selectedFont) FontWeight.Bold else FontWeight.Normal
                    )
                )
                if (font == selectedFont) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                }
            }
        }
    }

}

