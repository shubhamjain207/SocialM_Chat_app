package com.example.storyapp3.views

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(250, 243, 224)

                ), verticalArrangement = Arrangement.Center  // Soft cream background
        ) {
            // Image display
            AsyncImage(
                model = decodedUrl_final,
                contentDescription = "Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f) // Keep aspect ratio for images
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit
            )


                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth().padding(top = 30.dp)
                    ) {
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(containerColor = Color(224, 122, 95)) // Warm coral
                        ) {
                            Text("Save", color = Color.White)
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(containerColor = Color(224, 122, 95)) // Warm coral
                        ) {
                            Text("Share", color = Color.White)
                        }
                    }
                }
            }
}
