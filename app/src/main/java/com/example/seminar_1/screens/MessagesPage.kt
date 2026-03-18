package com.example.seminar_1.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seminar_1.components.NavigableMessagesButton
import com.example.seminar_1.components.SegmentedControl
import com.example.seminar_1.components.TextSizer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesPage() {
    var showSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            if (showSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showSheet = false }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(
                            16.dp,
                            alignment = Alignment.CenterVertically
                        ),
                    ) {
                        Text("T Vzhled a čtení", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                        Column(
                            verticalArrangement = Arrangement.spacedBy(
                                8.dp,
                                alignment = Alignment.CenterVertically
                            ),
                        ) {
                            Text(
                                "Velikost textu".uppercase(),
                                fontWeight = FontWeight.Medium,
                                fontSize = 10.sp
                            )
                            TextSizer()
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(
                                8.dp,
                                alignment = Alignment.CenterVertically
                            ),
                        ) {
                            Text(
                                "Řádkování".uppercase(),
                                fontWeight = FontWeight.Medium,
                                fontSize = 10.sp
                            )
                            SegmentedControl()
                        }
                    }
                }
            }
            IconButton(
                onClick = { showSheet = true },
            ) {
                Icon(
                    imageVector = Icons.Default.TextFields,
                    contentDescription = "Text edit"
                )
            }
            IconButton(
                onClick = { /* akce */ },
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        }
        NavigableMessagesButton()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MessagesPagePreview() {
    MessagesPage()
}