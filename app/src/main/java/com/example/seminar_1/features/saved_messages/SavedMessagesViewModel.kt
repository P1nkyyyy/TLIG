package com.example.seminar_1.features.saved_messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.seminar_1.TligApplication
import com.example.seminar_1.features.messages.data.model.MessageModel
import com.example.seminar_1.data.repository.MessageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SavedMessagesViewModel(private val repository: MessageRepository) : ViewModel() {

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

    fun unarchiveMessages(ids: List<Int>) {
        viewModelScope.launch {
            ids.forEach { id ->
                repository.updateArchive(id, false)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TligApplication)
                SavedMessagesViewModel(repository = application.messageRepository)
            }
        }
    }
}
