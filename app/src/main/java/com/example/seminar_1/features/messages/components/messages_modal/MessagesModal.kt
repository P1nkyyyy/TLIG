package com.example.seminar_1.features.messages.components.messages_modal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.seminar_1.features.messages.data.model.MessageModel
import com.example.seminar_1.utils.groupMessagesForModal
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesModal(
    onDismissRequest: () -> Unit,
    messages: List<MessageModel>,
    loadMessage: (id: Int) -> Unit,
    onToggleArchive: (message: MessageModel) -> Unit,
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
                                            .padding(start = 8.dp)
                                    ) {
                                        messagesInMonth.forEach { message ->
                                            MessageItem(
                                                message = message,
                                                onItemClick = {
                                                    scope.launch { sheetState.hide() }
                                                        .invokeOnCompletion {
                                                            if (!sheetState.isVisible) onDismissRequest()
                                                        }
                                                    loadMessage(message.id)
                                                },
                                                onToggleArchive = { onToggleArchive(message) }
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