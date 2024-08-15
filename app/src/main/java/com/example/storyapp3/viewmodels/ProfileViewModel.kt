package com.example.storyapp3.viewmodels

import android.kotlin.foodclub.api.authentication.PublicPhoto
import android.kotlin.foodclub.api.authentication.PublicUser
import android.kotlin.foodclub.api.authentication.UserSignInInformation
import android.kotlin.foodclub.api.retrofit.RetrofitInstance
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {

    private val _photoList = MutableStateFlow<List<PublicPhoto>>(emptyList())
    val photoList: StateFlow<List<PublicPhoto>> get() = _photoList


    fun getPhotosOfUsers(userObj: PublicUser) {

//        var list = arrayListOf<PublicPhoto>()


        viewModelScope.launch {

            var response = RetrofitInstance.retrofitApi.getPhotosOfUsers(userObj)

            Log.i("Check1 ==>",response.body().toString())


            _photoList.value = response.body() as ArrayList<PublicPhoto>

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