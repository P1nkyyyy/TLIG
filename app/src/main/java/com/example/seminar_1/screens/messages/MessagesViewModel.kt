package com.example.seminar_1.screens.messages

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.seminar_1.TligApplication
import com.example.seminar_1.data.repository.MessageRepository
import com.example.seminar_1.data_classes.MessageType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MessagesViewModel(private val repository: MessageRepository) : ViewModel() {
    private val _currentMessage = MutableStateFlow<MessageType?>(null)
    val currentMessage: StateFlow<MessageType?> = _currentMessage.asStateFlow()

    private val _allMessages = MutableStateFlow<List<MessageType>>(emptyList())
    val allMessages: StateFlow<List<MessageType>> = _allMessages.asStateFlow()

    var currentMessageId by mutableIntStateOf(1)
        private set

    /* State variables for the message reader */
    var textSize by mutableIntStateOf(18)
    var lineHeight by mutableFloatStateOf(1.5f)
    var selectedFont by mutableStateOf("Serif")
    var backgroundColor by mutableStateOf(Color(0xFF1B2536))
    var contentColor by mutableStateOf(Color(0xFFE3EAF3))

    init {
        loadMessage(currentMessageId)
        loadAllMessages()
    }
    fun loadMessage(id: Int) {
        currentMessageId = id
        viewModelScope.launch {
            repository.getMessageById(id).collectLatest { message ->
                _currentMessage.value = message
            }
        }
    }

    private fun loadAllMessages() {
        viewModelScope.launch {
            repository.getAllMessages().collect {
                _allMessages.value = it
            }
        }
    }

    fun nextMessage() {
        loadMessage(currentMessageId + 1)
    }

    fun previousMessage() {
        if (currentMessageId > 1) {
            loadMessage(currentMessageId - 1)
        }
    }

    fun updateTextSize(size: Int) {
        textSize = size
    }

    fun updateFont(font: String) {
        selectedFont = font
    }

    fun updateBackground(bg: Color, content: Color) {
        backgroundColor = bg
        contentColor = content
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TligApplication)
                MessagesViewModel(repository = application.messageRepository)
            }
        }
    }
}