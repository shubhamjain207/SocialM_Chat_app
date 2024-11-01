package com.example.storyapp3.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.kotlin.foodclub.api.authentication.UploadPhotoInformation
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.canhub.cropper.CropImage.CancelledResult.uriContent
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.example.storyapp3.R
import com.example.storyapp3.viewmodels.PhotoEditorViewModel
import com.example.storyapp3.viewmodels.ProfileViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoEditor(navController: NavHostController, photoId: String?) {

    val context = LocalContext.current

    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val username = ReadDataFromSharedPreferences(LocalContext.current)
    val photoEditorViewModel: PhotoEditorViewModel = viewModel()
    var photoUploadBtn by remember { mutableStateOf<Boolean?>(true) }


    val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            imageUri = result.uriContent
        } else {
            val exception = result.error
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(250, 243, 224)

            ), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally// Soft cream background
    ) {
        if (imageUri != null) {

            AsyncImage(model = imageUri,
                contentDescription = "Image",
                modifier = Modifier
                    .clickable { },
                contentScale = ContentScale.Fit

            )

        } else {
            Image(
                painter = painterResource(id = R.drawable.uploadphotoplaceholder),
                contentDescription = null,
                modifier = Modifier.size(150.dp)

            )
        }


        Box(modifier = Modifier.padding(top = 100.dp)) {

            if (photoUploadBtn == true) {

//            Row(
//                modifier = Modifier
//                    .align(Alignment.BottomEnd)
//                    .padding(20.dp)
//            ) {

                if(imageUri == null){
                    Button(onClick = {
                        val cropOption = CropImageContractOptions(uriContent, CropImageOptions().apply {
                            cropShape =
                                CropImageView.CropShape.RECTANGLE  // Use rectangle shape for Instagram-like cropping
                            guidelines =
                                CropImageView.Guidelines.ON       // Optional: Show crop guidelines
                            aspectRatioX =
                                4                         // Set aspect ratio width (4 for 4:5, 1 for square)
                            aspectRatioY =
                                5                         // Set aspect ratio height (5 for 4:5, 1 for square)
                            fixAspectRatio = true

                        })
                        imageCropLauncher.launch(cropOption)
                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(224, 122, 95), // Warm coral background color
                            contentColor = Color.White // Text color
                        )
                    ) {
                        Text(text = "Pick Photo")
                    }
                }else{
                    Button(onClick = {

                        if (imageUri == null) {
                            Toast.makeText(context, "Please pick an image", Toast.LENGTH_LONG).show();

                            return@Button;
                        }

                        photoUploadBtn = false

                        val storageRef: StorageReference = FirebaseStorage.getInstance().getReference()
                            .child("Images/${username + "_" + System.currentTimeMillis()}")
                        val uploadTask = storageRef.putFile(imageUri!!)

                        uploadTask.addOnSuccessListener { taskSnapshot ->

                            storageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                                val photoObj = UploadPhotoInformation(username, downloadUrl.toString())
                                photoEditorViewModel.uploadPhoto(photoObj, navController)

                            }.addOnFailureListener { exception ->
                                Log.i("Firebase exception---->", exception.toString())
                            }
                        }
                    },  colors = ButtonDefaults.buttonColors(
                        containerColor = Color(224, 122, 95), // Warm coral background color
                        contentColor = Color.White // Text color
                    )
                    ) {
                        Text(text = "Upload Photo")
                    }
                }

                //  }
            } else {
                Row(

                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(42.dp),
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
            }

        }

    }



}