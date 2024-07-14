package com.example.storyapp3.viewmodels

import android.kotlin.foodclub.api.authentication.PublicUser
import android.kotlin.foodclub.api.authentication.UserSignInInformation
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatListViewModel: ViewModel() {

    private val _usersList = MutableStateFlow<List<PublicUser>>(listOf())
    val usersList: StateFlow<List<PublicUser>> get() = _usersList

    fun getUsers(){
        viewModelScope.launch {
            var response = RetrofitInstance.retrofitApi.getAllUsers()
            _usersList.value = response.body()!!

        }
    }

}