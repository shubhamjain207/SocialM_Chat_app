package com.example.storyapp3.viewmodels

import android.kotlin.foodclub.api.authentication.UserSignUpInformation
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RegisterViewModel:ViewModel() {

        fun registerUser(user:UserSignUpInformation){
            viewModelScope.launch {
                var response = RetrofitInstance.retrofitApi.registerUser(user)
                Log.i("User---->",response.body().toString())
            }
        }
}


