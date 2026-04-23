package com.example.seminar_1.features.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seminar_1.data.repository.MessageRepository
import com.example.seminar_1.features.home.data.model.FeastCelebrationsModel
import com.example.seminar_1.features.home.data.network.FeastNetworkClient
import com.example.seminar_1.features.messages.data.model.MessageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MessageRepository) : ViewModel() {
    private val _feastCelebrations = MutableStateFlow(emptyList<FeastCelebrationsModel>())
    val feastCelebrations: StateFlow<List<FeastCelebrationsModel>> =
        _feastCelebrations.asStateFlow()


    private val _messageOnDay = MutableStateFlow<MessageModel?>(null)
    val messageOnDay: StateFlow<MessageModel?> = _messageOnDay.asStateFlow()

    private val _messageToContinue = MutableStateFlow<MessageModel?>(null)
    val messageToContinue: StateFlow<MessageModel?> = _messageToContinue

    init {
        fetchFeastCelebrations()
        fetchMessageOnDay()
        fetchMessageToContinue()
    }

    private fun fetchFeastCelebrations() {
        viewModelScope.launch {
            try {
                val response = FeastNetworkClient.apiService.getTodayFeast()
                _feastCelebrations.value = response.celebrations
                Log.d("HomeViewModel", "Dnesní svátek: $response")
            } catch (error: Exception) {
                Log.e("HomeViewModel", "Chyba při načítání dnešního svátku", error)
                _feastCelebrations.value = emptyList()
            }
        }
    }

    private fun fetchMessageOnDay() {
        viewModelScope.launch {
            val message = repository.getDailyMessage()
            _messageOnDay.value = message
        }
    }

    private fun fetchMessageToContinue() {
        viewModelScope.launch {
            repository.getLastOpenedMessage().collectLatest { message ->
                _messageToContinue.value = message
            }
        }
    }

    fun updateArchive(message: MessageModel) {
        viewModelScope.launch {
            repository.updateArchive(message.id, !message.isArchived)
        }
    }
}