package com.example.seminar_1.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seminar_1.ui.theme.Seminar1Theme

@Composable
fun HomePage() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Poselství na den")
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            ) {
                Text(
                    "Den Mého Návratu je blíže, než si myslíte",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text("31. ledna 2019", fontSize = 12.sp)
                Spacer(Modifier.height(16.dp))
                Text("Ó, Pane, veď mou duši po stezkách Věčného Života, veď Svou Církev k Jednotě, kéž je Tvé Poselství, se vším jeho bohatstvím, přijato celým Tvým stvořením! Oživ nás, oživ Svou Církev, ať Rozkvete a vydá svou vůni…")
                Box(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        Icons.Default.Bookmark,
                        contentDescription = "Bookmark",
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePagePreview() {
    Seminar1Theme {
        HomePage()
    }
}