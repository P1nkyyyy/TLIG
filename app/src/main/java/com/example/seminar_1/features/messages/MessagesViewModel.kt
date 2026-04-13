package com.example.seminar_1.features.messages

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
import com.example.seminar_1.features.messages.data.model.MessageModel
import com.example.seminar_1.data.repository.MessageRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MessagesViewModel(private val repository: MessageRepository) : ViewModel() {
    /**
     * Messages
     */
    private val _currentMessage = MutableStateFlow<MessageModel?>(null)

    val currentMessage: StateFlow<MessageModel?> = _currentMessage.asStateFlow()

    private var currentMessageJob: Job? = null

    private val _allMessages = MutableStateFlow<List<MessageModel>>(emptyList())

    val allMessages: StateFlow<List<MessageModel>> = _allMessages.asStateFlow()

    var isArchived by mutableStateOf(false)

    var currentMessageId by mutableIntStateOf(-1)
        private set

    init {
        loadAllMessages()
    }

    fun loadMessage(id: Int) {
        currentMessageId = id
        currentMessageJob?.cancel()

        viewModelScope.launch {
            repository.updateLastOpenedMessage(id, System.currentTimeMillis())
        }

        currentMessageJob = viewModelScope.launch {
            repository.getMessageById(id).collectLatest { message ->
                _currentMessage.value = message
                isArchived = message.isArchived
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

    /* #region ThemeSettings */
    var textSize by mutableIntStateOf(18)
    var lineHeight by mutableFloatStateOf(1.5f)
    var selectedFont by mutableStateOf("Serif")
    var backgroundColor by mutableStateOf(Color(0xFF1B2536))
    var contentColor by mutableStateOf(Color(0xFFE3EAF3))
    fun updateTextSize(size: Int) {
        textSize = size
    }

    fun updateFont(font: String) {
        selectedFont = font
    }

    fun updateThemeColor(bg: Color, content: Color) {
        backgroundColor = bg
        contentColor = content
    }

    fun updateLineHeight(height: Float) {
        lineHeight = height
    }
    /* #endregion ThemeSettings */

    fun updateArchive(message: MessageModel) {
        viewModelScope.launch {
            val newArchiveStatus = !message.isArchived
            repository.updateArchive(message.id, newArchiveStatus)

            if (message.id == currentMessageId) {
                isArchived = newArchiveStatus
            }
        }
    }

    fun updateLastReadParagraph(messageId: Int, paragraphIndex: Int) {
        viewModelScope.launch {
            repository.updateLastReadParagraph(messageId, paragraphIndex)
        }
    }

    /**
     * Notes
     */
    private val _currentNote = MutableStateFlow<String?>(null)

    val currentNote: StateFlow<String?> = _currentNote.asStateFlow()

    fun loadNote(noteId: Int, messageId: Int) {
        viewModelScope.launch {
            repository.getNoteById(noteId, messageId).collectLatest { note ->
                _currentNote.value = note?.content
            }
        }
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