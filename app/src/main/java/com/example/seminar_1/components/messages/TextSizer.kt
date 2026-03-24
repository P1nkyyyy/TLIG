package com.example.seminar_1.components.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.seminar_1.R
import com.example.seminar_1.ui.theme.Seminar1Theme

@Composable
fun TextSizer(
    textSize: Int,
    onTextSize: (Int) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        IconButton(
            onClick = { onTextSize(textSize - 1) }
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = stringResource(R.string.messages_modal_item_text_size_decrease),
                tint = Color.White,
                modifier = Modifier
                    .background(Color(0xFF5B5B5B))
                    .padding(8.dp)
            )
        }
        Text(
            text = textSize.toString(),
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .border(3.dp, Color(0xFF5B5B5B), shape = RoundedCornerShape(24.dp))
                .background(Color(0xFF252525), shape = RoundedCornerShape(24.dp))
                .padding(8.dp)
        )
        IconButton(
            onClick = {onTextSize(textSize + 1)}
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.messages_modal_item_text_size_increase),
                tint = Color.White,
                modifier = Modifier
                    .background(Color(0xFF5B5B5B))
                    .padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TextSizerPreview() {
    Seminar1Theme {
        TextSizer(18, {})
    }
}