package com.example.seminar_1.features.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seminar_1.R
import com.example.seminar_1.features.home.data.model.FeastCelebrationsModel

@Composable
fun FeastCard(
    feastCelebrations: List<FeastCelebrationsModel>,
) {
    val backgroundColor = Color(0xFF181D24) // Temně břidlicová
    val borderColor = Color(0xFF2A3441)     // Jemný okraj (slate-800)
    val goldColor = Color(0xFFC69C6D)       // Tlumená zlatá/okrová
    val iconBgColor = goldColor.copy(alpha = 0.15f) // Poloprůhledné pozadí ikony
    val subtitleColor = Color(0xFF94A3B8)   // slate-400
    val titleColor = Color(0xFFE2E8F0)      // slate-200

    val cardShape = RoundedCornerShape(12.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(cardShape)
            .background(backgroundColor)
            .border(1.dp, borderColor, cardShape)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(iconBgColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.DateRange,
                contentDescription = stringResource(R.string.home_feast_card_icon_alt),
                tint = goldColor,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = stringResource(R.string.home_feast_card_title),
                color = subtitleColor,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            feastCelebrations.forEach { feast ->
                Text(
                    text = feast.title,
                    color = titleColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF11141A)
@Composable
fun FeastCardPreview() {
    val mockFeastCelebrations = listOf(
        FeastCelebrationsModel("Památka sv. Jana od Kříže", "", "", 1.2),
//        FeastCelebrationsModel("Památka sv. Jana od Kříže", "", "", 1.2)
    )
    Box(modifier = Modifier.padding(16.dp)) {
        FeastCard(mockFeastCelebrations)
    }
}