package com.example.seminar_1.components.messages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seminar_1.AppDatabase
import com.example.seminar_1.data_classes.MessageType
import com.example.seminar_1.utils.removeNoteParser
import kotlin.collections.emptyList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesModal(onDismissRequest: () -> Unit) {
    // Database
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val dao = db.messagesDao()

    val messages by dao.getAll().collectAsState(initial = null)

    val groupedData = groupMessagesForModal(messages ?: emptyList())

    ModalBottomSheet(
        onDismissRequest
    ) {
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
//                Text(
//                    text = "1993",
//                    fontWeight = FontWeight.SemiBold,
//                    fontSize = 24.sp
//                )
//                IconButton(
//                    onClick = onDismissRequest,
//                    modifier = Modifier.align(Alignment.CenterEnd)
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Close,
//                        contentDescription = "Close bottom sheet"
//                    )
//                }

                LazyColumn {
                    groupedData.forEach { (year, monthsMap) ->
                        item {
                            Text(text = year, fontSize = 24.sp)
                        }

                        monthsMap.forEach { (month, messagesInMonth) ->
                            item {
                                Text(text = month, fontSize = 16.sp)
                            }

                            items(messagesInMonth) { message ->
                                 Text(removeNoteParser(message.date))
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getYearFromDate(datum: String): String {
    return datum.split(" ").lastOrNull() ?: "Neznámý rok"
}

fun getMonthFromDate(datum: String): String {
    return datum.split(" ")[1].uppercase()
}

fun groupMessagesForModal(allMessages: List<MessageType>): Map<String, Map<String, List<MessageType>>> {
    return allMessages
        // Krok A: Seskupíme celý seznam do skupin podle Roku (Map<Rok, List>)
        .groupBy { message -> getYearFromDate(removeNoteParser(message.date)) }

        // Krok B: Projdeme každý vytvořený Rok a jeho seznam poselství
        // znovu seskupíme, tentokrát podle Měsíce
        .mapValues { (_, messagesInYear) ->
            messagesInYear.groupBy { message -> getMonthFromDate(removeNoteParser(message.date)) }
        }
}