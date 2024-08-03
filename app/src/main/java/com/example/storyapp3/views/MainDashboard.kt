package com.example.storyapp3.views

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.kotlin.foodclub.api.authentication.PublicPhoto
import android.kotlin.foodclub.api.authentication.PublicUser
import android.kotlin.foodclub.api.authentication.UploadPhotoInformation
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.storyapp3.R
import com.example.storyapp3.viewmodels.MainDashboardViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.delay



fun uploadImage(
    selectedImageUri: Uri,
    viewModel: MainDashboardViewModel,
    navController: NavHostController,
    username:String
){

    val storageRef:StorageReference  = FirebaseStorage.getInstance().getReference().child("Images/${username + "_" + System.currentTimeMillis()}");
    val uploadTask = storageRef.putFile(selectedImageUri)

    Log.i("Check 1 ----------->","<---checked--->")

    uploadTask.addOnSuccessListener { taskSnapshot->

        Log.i("Check 2 ----------->","<---checked--->")

        storageRef.downloadUrl.addOnSuccessListener { downloadUrl->

            val photoObj = UploadPhotoInformation(username, downloadUrl.toString())
            viewModel.uploadPhoto(photoObj,navController)

        }.addOnFailureListener{ exception->
            Log.i("Firebase exception---->",exception.toString())
        }
    }

}

@Composable
fun MainDashboard(navController: NavHostController){

    var viewModel: MainDashboardViewModel = viewModel()

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.getAllPhotos()
            delay(6000) // Delay for 60 seconds (60000 milliseconds)
        }
    }

    val username =  ReadDataFromSharedPreferences(LocalContext.current)



    var selectedImageURI by remember {
        mutableStateOf<Uri?>(null)
    }
//
//    var selectedImageURIs by remember {
//        mutableStateOf<List<Uri>>(emptyList())
//    }
//
//    var sampleImageIds by remember {
//        mutableStateOf<List<PublicPhoto>>(emptyList())
//    }

    val photoList by viewModel.photoList.collectAsState()


    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri->selectedImageURI = uri
        Log.i("Picked Image--->",selectedImageURI.toString())
                uploadImage(selectedImageURI!!, viewModel, navController, username)
        })

    LazyColumn(modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(photoList){ imageid->

            Text(text = imageid.username)
            AsyncImage(model = imageid.photoUrl,
                contentDescription ="Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color.LightGray)
                    .padding(vertical = 10.dp),
                contentScale = ContentScale.Fit,
                )
        }
    }

    Log.i("Image Uri---->",selectedImageURI.toString());

    Box(modifier = Modifier.fillMaxSize()){
        Row(modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(20.dp)) {
            Button(onClick = { singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            ) }) {
                Text(text = "Pick Photo")
            }

        }
    }


}

