package com.example.storyapp3.viewmodels

import android.kotlin.foodclub.api.authentication.PublicUser
import android.kotlin.foodclub.api.authentication.UserSignInInformation
import android.kotlin.foodclub.api.authentication.UserSignUpInformation
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel:ViewModel() {

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage: StateFlow<String> get() = _errorMessage

    fun loginUser(user: UserSignInInformation,navController:NavHostController){
        viewModelScope.launch {
            var response = RetrofitInstance.retrofitApi.loginUser(user)
            if(response.body()!!.message == "Login Successfull"){
                navController.navigate("MAINDASHBOARD")
            }
            else if(response.body()!!.message == "Incorrect Password"){
                _errorMessage.value = "Incorrect Password."
            }
            else if(response.body()!!.message == "User name not found."){
                _errorMessage.value = "User name not found."
            }
        }
    }


}