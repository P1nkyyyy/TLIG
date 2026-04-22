package com.example.seminar_1.features.saved_messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seminar_1.data.repository.MessageRepository
import com.example.seminar_1.features.messages.data.model.MessageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedMessagesViewModel @Inject constructor(private val repository: MessageRepository) : ViewModel() {

    private val _messages = MutableStateFlow<List<MessageModel>>(emptyList())
    val messages: StateFlow<List<MessageModel>> = _messages.asStateFlow()

    init {
        loadAllArchivedMessages()
    }

    private fun loadAllArchivedMessages() {
        viewModelScope.launch {
            repository.getAllMessages().collectLatest {
                _messages.value = it.filter { message -> message.isArchived }
            }
        }
    }

    fun unarchiveMessage(id: Int) {
        viewModelScope.launch {
            repository.updateArchive(id, false)
        }
    }

    fun unarchiveMessages(ids: List<Int>) {
        viewModelScope.launch {
            ids.forEach { id ->
                repository.updateArchive(id, false)
            }
        }
    }
}
