package com.example.storyapp3.viewmodels

import android.kotlin.foodclub.api.authentication.PublicPhoto
import android.kotlin.foodclub.api.authentication.UploadPhotoInformation
import android.kotlin.foodclub.api.authentication.UserSignInInformation
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainDashboardViewModel:ViewModel() {

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage: StateFlow<String> get() = _errorMessage

    private val _photoList = MutableStateFlow<List<PublicPhoto>>(emptyList())
    val photoList: StateFlow<List<PublicPhoto>> get() = _photoList


    fun uploadPhoto(photo: UploadPhotoInformation, navController: NavHostController){
        viewModelScope.launch {
            var response = RetrofitInstance.retrofitApi.uploadPhoto(photo)
            if(response.body()!!.message == "Upload Successfull"){
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

    fun getAllPhotos() {

//        var list = arrayListOf<PublicPhoto>()

        viewModelScope.launch {
            var response = RetrofitInstance.retrofitApi.getAllPhotos()

            _photoList.value = response.body() as ArrayList<PublicPhoto>


            Log.i("List=========>",response.body()!!::class.toString())
//            if(response.body()!!.message == "Upload Successfull"){
//                navController.navigate("MAINDASHBOARD")
//            }
//            else if(response.body()!!.message == "Incorrect Password"){
//                _errorMessage.value = "Incorrect Password."
//            }
//            else if(response.body()!!.message == "User name not found."){
//                _errorMessage.value = "User name not found."
//            }
        }

    }




}