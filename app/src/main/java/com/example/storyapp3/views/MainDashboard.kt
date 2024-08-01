package com.example.storyapp3.views

import android.kotlin.foodclub.api.authentication.PublicPhoto
import android.kotlin.foodclub.api.authentication.PublicUser
import android.kotlin.foodclub.api.authentication.UploadPhotoInformation
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.storyapp3.R
import com.example.storyapp3.viewmodels.MainDashboardViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


fun uploadImage(
    selectedImageUri: Uri,
    viewModel: MainDashboardViewModel,
    navController: NavHostController
){

    val storageRef:StorageReference  = FirebaseStorage.getInstance().getReference().child("Images/${222}");
    val uploadTask = storageRef.putFile(selectedImageUri)

    Log.i("Check 1 ----------->","<---checked--->")

    uploadTask.addOnSuccessListener { taskSnapshot->

        Log.i("Check 2 ----------->","<---checked--->")

        storageRef.downloadUrl.addOnSuccessListener { downloadUrl->
            Log.i("Firebase upload Successfull--->","Download Url $downloadUrl")

            val photoObj = UploadPhotoInformation(downloadUrl.toString())

            viewModel.uploadPhoto(photoObj,navController)

        }.addOnFailureListener{ exception->
            Log.i("Firebase exception---->",exception.toString())

        }
    }



}

@Composable
fun MainDashboard(navController: NavHostController){

    var viewModel: MainDashboardViewModel = viewModel()


    var selectedImageURI by remember {
        mutableStateOf<Uri?>(null)
    }

    var selectedImageURIs by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }

    var sampleImageIds by remember {
        mutableStateOf<List<PublicPhoto>>(emptyList())
    }

    val photoList by viewModel.photoList.collectAsState()


    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri->selectedImageURI = uri
        Log.i("Picked Image--->",selectedImageURI.toString())
                uploadImage(selectedImageURI!!, viewModel, navController)
        })

    LazyColumn(modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(photoList){ imageid->
            AsyncImage(model = imageid.photoUrl, contentDescription ="Image", modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.Crop)
        }
    }

    Log.i("Image Uri---->",selectedImageURI.toString());
    
    Button(onClick = { singlePhotoPickerLauncher.launch(
        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
    ) }) {
            Text(text = "Pick Photo")
    }



    Log.i("final List ====>",photoList.toString())

    Button(onClick = {viewModel.getAllPhotos() }) {

        Text(text = "Get all photos")

    }
    
    
}