package com.example.seminar_1.screens.messages.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seminar_1.data.model.MessageModel
import com.example.seminar_1.utils.groupMessagesForModal
import com.example.seminar_1.utils.removeNoteParser
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesModal(
    onDismissRequest: () -> Unit,
    messages: List<MessageModel>,
    loadMessage: (id: Int) -> Unit,
) {
    val groupedData = remember(messages) { groupMessagesForModal(messages) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var expandedYear by remember { mutableStateOf<String?>(null) }
    var expandedMonth by remember { mutableStateOf<String?>(null) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 700.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            groupedData.forEach { (year, monthsMap) ->
                val isYearExpanded = expandedYear == year

                item {
                    YearHeader(
                        year = year,
                        isExpanded = isYearExpanded,
                        onClick = {
                            expandedYear = if (isYearExpanded) null else year
                            expandedMonth = null
                        }
                    )
                }

                item {
                    AnimatedVisibility(
                        visible = isYearExpanded,
                        enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
                        exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            monthsMap.forEach { (month, messagesInMonth) ->
                                val isMonthExpanded = expandedMonth == month

                                MonthHeader(
                                    month = month,
                                    isExpanded = isMonthExpanded,
                                    onClick = {
                                        expandedMonth = if (isMonthExpanded) null else month
                                    }
                                )

                                AnimatedVisibility(
                                    visible = isMonthExpanded,
                                    enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
                                    exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 16.dp)
                                    ) {
                                        messagesInMonth.forEach { message ->
                                            MessageItem(
                                                message = message,
                                                onClick = {
                                                    scope.launch { sheetState.hide() }
                                                        .invokeOnCompletion {
                                                            if (!sheetState.isVisible) onDismissRequest()
                                                        }
                                                    loadMessage(message.id)
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun YearHeader(
    year: String,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = year,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Icon(
                imageVector = if (isExpanded) Icons.Default.ExpandMore else Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun MonthHeader(
    month: String,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = month.uppercase(),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
            Icon(
                imageVector = if (isExpanded) Icons.Default.ExpandMore else Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.DarkGray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun MessageItem(
    message: MessageModel,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = removeNoteParser(message.date),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                maxLines = 1,
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 24.dp),
                text = message.title,
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray,
                textAlign = TextAlign.End,
                maxLines = 1,
            )
        }
    }
}