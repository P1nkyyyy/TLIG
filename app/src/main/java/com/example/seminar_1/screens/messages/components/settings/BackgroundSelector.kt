package com.example.seminar_1.screens.messages.components.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.seminar_1.data.model.BackgroundItemModel
import com.example.seminar_1.ui.theme.Seminar1Theme

@Composable
fun BackgroundSelector(
    onBackgroundColor: (Color, Color) -> Unit
) {
    val options = listOf(
        BackgroundItemModel("navy", Color(0xFF1B2536), Color.White, Color.White),
        BackgroundItemModel("black", Color(0xFF000000), Color.White, Color.White),
        BackgroundItemModel("white", Color(0xFFFFFFFF), Color(0xFF1A1A1A), Color(0xFF010101)),
        BackgroundItemModel("dark_grey", Color(0xFF333333), Color.White, Color.White),
        BackgroundItemModel("sepia", Color(0xFFF4ECD8), Color.White, Color(0xFF010101)),
    )

    var selectedId by remember { mutableStateOf(options[0].id) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEach { option ->
            BackgroundCard(
                option = option,
                isSelected = selectedId == option.id,
                onClick = {
                    selectedId = option.id
                    onBackgroundColor(option.containerColor, option.contentColor)
                }
            )
        }
    }
}

@Composable
fun BackgroundCard(
    option: BackgroundItemModel,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(65.dp)
            .height(90.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(option.containerColor)
            .clickable { onClick() }
            .padding(8.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val weights = listOf(1f, 0.7f, 0.9f, 1f, 0.8f)

            weights.forEach { weight ->
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(weight)
                        .align(Alignment.Start)
                        .height(3.dp)
                        .background(option.barColor, RoundedCornerShape(2.dp))
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .then(
                        if (isSelected) {
                            Modifier.background(option.contentColor)
                        } else {
                            Modifier.border(
                                2.dp,
                                option.contentColor.copy(alpha = 0.5f),
                                CircleShape
                            )
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = option.containerColor
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BackgroundSelectorPreview() {
    Seminar1Theme {
        BackgroundSelector(onBackgroundColor = { _, _ -> })
    }
}
