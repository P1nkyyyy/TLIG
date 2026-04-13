package com.example.seminar_1.features.messages.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FormatColorText
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.seminar_1.R

@Composable
fun MessagesSettingsMenu(
    backgroundColor: Color,
    contentColor: Color,
    isArchived: Boolean,
    onToggleArchive: () -> Unit,
    onOpenSettingsModal: () -> Unit,
    onOpenSearchModal: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides contentColor
        ) {
            IconButton(onClick = onToggleArchive) {
                Icon(
                    imageVector = if (isArchived) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.messages_settings_menu_text_edit),
                    modifier = Modifier.padding(8.dp),
                )
            }
            Row(
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onOpenSettingsModal) {
                    Icon(
                        imageVector = Icons.Default.FormatColorText,
                        contentDescription = stringResource(R.string.messages_settings_menu_text_edit),
                        modifier = Modifier.padding(8.dp),
                    )
                }
                IconButton(onClick = onOpenSearchModal) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.messages_settings_menu_search),
                        modifier = Modifier.padding(8.dp),
                    )
                }
            }
        }
    }
}