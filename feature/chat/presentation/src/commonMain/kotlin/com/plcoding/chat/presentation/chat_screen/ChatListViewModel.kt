package com.plcoding.chat.presentation.chat_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.auth.ISessionDataStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatListViewModel(
    private val dataStore: ISessionDataStorage,
) : ViewModel() {
    init {
        viewModelScope.launch {
            delay(5000)
            dataStore.clearAuthInfo()
        }

    }
}