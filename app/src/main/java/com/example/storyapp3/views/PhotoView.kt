package com.example.storyapp3.views

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoView(navController: NavHostController, photoId:String?) {

    var decodedUrl = Uri.decode(photoId)

    val decodedUrl_final = decodedUrl.replace("Images/","Images%2F")

    Column (){
        AsyncImage(
            model = decodedUrl_final,
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f) // Keep aspect ratio for images
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit
        ) 
    }
    


}