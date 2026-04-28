package com.example.seminar_1.features.messages.components.settings_modal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ViewAgenda
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seminar_1.R
import com.example.seminar_1.features.messages.data.model.ModalItemModel
import com.example.seminar_1.ui.components.SegmentedControl

@Composable
fun MainSettingsContent(
    textSize: Int,
    backgroundColor: Color,
    contentColor: Color,
    lineHeight: Float,
    fontFamily: String,
    onClose: () -> Unit,
    onTextSizeChange: (Int) -> Unit,
    onOpenFontSelect: () -> Unit,
    onThemeColorsChange: (Color, Color) -> Unit,
    onLineHeightChange: (Float) -> Unit,
    onResetToDefault: () -> Unit,
) {
    val lineHeightOptions = listOf(1.2f, 1.5f, 2.0f)
    val lineHeightIcons = listOf(
        Icons.Default.Menu,
        Icons.AutoMirrored.Filled.List, Icons.Default.ViewAgenda
    )
    val currentLineHeightIndex = lineHeightOptions.indexOf(lineHeight).coerceAtLeast(0)

    val items = listOf(
        ModalItemModel(stringResource(R.string.messages_settings_modal_text_size_title), {
            TextSizer(textSize, onTextSizeChange)
        }),
        ModalItemModel(stringResource(R.string.messages_settings_modal_line_height_title), {
            SegmentedControl(
                items = lineHeightIcons,
                selectedIndex = currentLineHeightIndex,
                onItemSelected = { index ->
                    onLineHeightChange(lineHeightOptions[index])
                }
            )
        }),
        ModalItemModel(stringResource(R.string.messages_settings_modal_font_title), {
            FontChanger(fontFamily, onOpenFontSelect)
        }),
        ModalItemModel(stringResource(R.string.messages_settings_modal_bg_selection_title), {
            BackgroundSelector(backgroundColor, contentColor, onThemeColorsChange)
        }),
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
                text = stringResource(R.string.messages_settings_modal_title),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
            IconButton(
                onClick = onClose,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.modal_close_button_text)
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
                    text = item.text.uppercase(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                item.component()
            }
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)

        TextButton(onClick = onResetToDefault) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
            Text(
                "Výchozí nastavení".uppercase(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}