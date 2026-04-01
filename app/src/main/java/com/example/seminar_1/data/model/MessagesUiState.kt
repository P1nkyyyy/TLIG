package com.example.seminar_1.data.model

data class MessagesUiState(
    val currentMessage: MessageModel? = null,
    val allMessages: List<MessageModel> = emptyList(),
    val currentMessageId: Int = 1,
    val isLoading: Boolean = false,
    val currentNote: String? = null,
    val readerSettings: ReaderSettings = ReaderSettings()
)
