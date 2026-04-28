package com.example.seminar_1.features.messages

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seminar_1.data.repository.MessageRepository
import com.example.seminar_1.features.messages.data.local.ThemePreferences
import com.example.seminar_1.features.messages.data.model.MessageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val repository: MessageRepository,
    private val themePreferences: ThemePreferences
) : ViewModel() {
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
    var textSize by mutableIntStateOf(themePreferences.textSize)
    var lineHeight by mutableFloatStateOf(themePreferences.lineHeight)
    var selectedFont by mutableStateOf(themePreferences.selectedFont)
    var backgroundColor by mutableStateOf(themePreferences.backgroundColor)
    var contentColor by mutableStateOf(themePreferences.contentColor)

    fun updateTextSize(size: Int) {
        textSize = size
        themePreferences.textSize = size
    }

    fun updateFont(font: String) {
        selectedFont = font
        themePreferences.selectedFont = font
    }

    fun updateThemeColor(bg: Color, content: Color) {
        backgroundColor = bg
        contentColor = content
        themePreferences.backgroundColor = bg
        themePreferences.contentColor = content
    }

    fun updateLineHeight(height: Float) {
        lineHeight = height
        themePreferences.lineHeight = height
    }

    fun resetThemeSettings() {
        themePreferences.resetToDefault()
        textSize = themePreferences.textSize
        lineHeight = themePreferences.lineHeight
        selectedFont = themePreferences.selectedFont
        backgroundColor = themePreferences.backgroundColor
        contentColor = themePreferences.contentColor
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
}