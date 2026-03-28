package com.example.seminar_1.components.messages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seminar_1.R
import com.example.seminar_1.data_classes.ModalItemType

@Composable
fun MainSettingsContent(
    onClose: () -> Unit,
    textSize: Int,
    onTextSize: (Int) -> Unit,
    fontFamily: String,
    onOpenFontSelect: () -> Unit,
    onBackgroundColor: (Color, Color) -> Unit
) {
    val items = listOf(
        ModalItemType(stringResource(R.string.messages_modal_item_text_size), { TextSizer(textSize, onTextSize) }),
        ModalItemType(stringResource(R.string.messages_modal_item_line_height), { SegmentedControl() }),
        ModalItemType(stringResource(R.string.messages_modal_item_font), { FontChanger(fontFamily, onOpenFontSelect) }),
        ModalItemType(stringResource(R.string.messages_modal_item_bg_selection), { BackgroundSelector(onBackgroundColor) }),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp, start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.spacedBy(
            20.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.messages_modal_title),
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
            IconButton(
                onClick = onClose,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.messages_modal_close_button_text)
                )
            }
        }

        items.forEach { item ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    8.dp,
                    alignment = Alignment.CenterVertically
                ),
            ) {
                Text(
                    item.text.uppercase(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                )
                item.component()
            }
        }
    }
}