package com.example.seminar_1.screens.saved_messages

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

class SavedMessagesViewModel(private val repository: MessageRepository) : ViewModel() {

    private val _messages = MutableStateFlow<List<MessageType>>(emptyList())
    val messages: StateFlow<List<MessageType>> = _messages.asStateFlow()

    init {
        loadAllMessages()
    }

    private fun loadAllMessages() {
        viewModelScope.launch {
            repository.getAllMessages().collectLatest { 
                _messages.value = it
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
