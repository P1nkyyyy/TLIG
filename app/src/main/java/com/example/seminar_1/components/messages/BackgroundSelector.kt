package com.example.seminar_1.components.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.seminar_1.data_classes.BackgroundItemType
import com.example.seminar_1.ui.theme.Seminar1Theme

@Composable
fun BackgroundSelector() {
    val options = listOf(
        BackgroundItemType("black", Color(0xFF000000), Color.White, Color.White),
        BackgroundItemType("white", Color(0xFFE0E0E0), Color(0xFF333333), Color(0xFF888888)),
        BackgroundItemType("dark_grey", Color(0xFF333333), Color.White, Color.White),
        BackgroundItemType("sepia", Color(0xFF443C32), Color(0xFFD2B48C), Color.White),
        BackgroundItemType("navy", Color(0xFF1B2536), Color(0xFF4A5D7E), Color.White)
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
                onClick = { selectedId = option.id }
            )
        }
    }
}

@Composable
fun BackgroundCard(
    option: BackgroundItemType,
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
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier.fillMaxWidth().height(3.dp).background(option.barColor, RoundedCornerShape(2.dp)))
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier.fillMaxWidth(0.7f).align(Alignment.Start).height(3.dp).background(option.barColor, RoundedCornerShape(2.dp)))
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier.fillMaxWidth(0.9f).align(Alignment.Start).height(3.dp).background(option.barColor, RoundedCornerShape(2.dp)))
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier.fillMaxWidth().height(3.dp).background(option.barColor, RoundedCornerShape(2.dp)))
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier.fillMaxWidth(0.8f).align(Alignment.Start).height(3.dp).background(option.barColor, RoundedCornerShape(2.dp)))

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .then(
                        if (isSelected) {
                            Modifier.background(option.contentColor)
                        } else {
                            Modifier.border(2.dp, option.contentColor.copy(alpha = 0.5f), CircleShape)
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
            BackgroundSelector()
    }
}
