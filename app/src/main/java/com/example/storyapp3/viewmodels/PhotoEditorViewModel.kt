package com.example.storyapp3.viewmodels

import android.kotlin.foodclub.api.authentication.UploadPhotoInformation
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

class PhotoEditorViewModel : ViewModel() {


    fun uploadPhoto(photo: UploadPhotoInformation, navController: NavHostController){
        viewModelScope.launch {
            var response = RetrofitInstance.retrofitApi.uploadPhoto(photo)


            if(response.isSuccessful){
                navController.navigate("MAINDASHBOARD")
            }
//            else if(response.body()!!.message == "Incorrect Password"){
//                _errorMessage.value = "Incorrect Password."
//            }
//            else if(response.body()!!.message == "User name not found."){
//                _errorMessage.value = "User name not found."
//            }
        }
    }

}