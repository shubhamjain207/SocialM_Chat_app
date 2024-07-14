package com.example.storyapp3.viewmodels

import android.kotlin.foodclub.api.authentication.MessageResponse
import android.kotlin.foodclub.api.authentication.MessageSent
import android.kotlin.foodclub.api.authentication.PublicUser
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Collections

class ChatRoomViewModel: ViewModel() {

    private val _chatMessagesList = MutableStateFlow<List<MessageResponse>>(listOf())
    val chatMessagesList: StateFlow<List<MessageResponse>> get() = _chatMessagesList


    fun sendMessage(message:MessageSent){
        viewModelScope.launch {
            var response = RetrofitInstance.retrofitApi.sendMessage(message)
        }
    }

    fun getAllMessages(sender:String,receiver:String){
        viewModelScope.launch {
            var response = RetrofitInstance.retrofitApi.getAllChatMessages(sender,receiver)
            _chatMessagesList.value = response.body()!!

        }
    }



}