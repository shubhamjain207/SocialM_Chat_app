//package com.example.storyapp3.views
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.content.Context.MODE_PRIVATE
//import android.kotlin.foodclub.api.authentication.PublicUser
//import android.util.Log
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import coil.compose.AsyncImage
//import coil.compose.rememberAsyncImagePainter
//import com.example.storyapp3.viewmodels.LoginViewModel
//import com.example.storyapp3.viewmodels.ProfileViewModel
//import kotlinx.coroutines.delay
//
//
//@SuppressLint("StateFlowValueCalledInComposition")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun Profile(navController: NavHostController){
//
//
//    var viewModel: ProfileViewModel = viewModel()
//    var context = LocalContext.current;
//
//    val username =  ReadDataFromSharedPreferences(LocalContext.current)
//
//    val usernameObj = PublicUser(username)
//
//    LaunchedEffect(Unit) {
//        while (true) {
//            viewModel.getPhotosOfUsers(usernameObj)
//            delay(6000)
//        }
//    }
//
//
//    val photoList by viewModel.photoList.collectAsState()
//
//    Log.i("Photo list in profile view====>",photoList.toString())
//    Column(
//        Modifier
//            .fillMaxSize()
//            .background(Color.White)
//            .padding(top = 40.dp, start = 30.dp, end = 30.dp),
//    horizontalAlignment = Alignment.CenterHorizontally,
//    verticalArrangement = Arrangement.spacedBy(35.dp)
//    ) {
//
//        Text(username);
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(3), // 2 columns in the grid
//            modifier = Modifier.fillMaxSize(),
//            content = {
//                items(photoList) { imageid -> // Example: 20 items in the grid
//                    AsyncImage(model = imageid.photoUrl,
//                        contentDescription ="Image",
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .size(150.dp)
//                            .padding(vertical = 10.dp),
//                        contentScale = ContentScale.Crop,
//                    )
//                }
//            }
//        )
//
//    }
//
//}
//
