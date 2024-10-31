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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.storyapp3.R
import com.example.storyapp3.viewmodels.MainDashboardViewModel
import com.example.storyapp3.viewmodels.ProfileViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.delay


fun uploadImage(
    selectedImageUri: Uri,
    viewModel: MainDashboardViewModel,
    navController: NavHostController,
    username: String
) {
    val storageRef: StorageReference =
        FirebaseStorage.getInstance().getReference().child("Images/${username + "_" + System.currentTimeMillis()}")
    val uploadTask = storageRef.putFile(selectedImageUri)

    uploadTask.addOnSuccessListener { taskSnapshot ->

        storageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
            val photoObj = UploadPhotoInformation(username, downloadUrl.toString())
            viewModel.uploadPhoto(photoObj, navController)

        }.addOnFailureListener { exception ->
            Log.i("Firebase exception---->", exception.toString())
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainDashboard(navController: NavHostController) {
    val homeViewModel: MainDashboardViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()

    val username = ReadDataFromSharedPreferences(LocalContext.current)

    val usernameObj = PublicUser(username)

    LaunchedEffect(Unit) {
        while (true) {
            homeViewModel.getAllPhotos()
            delay(6000) // Delay for 60 seconds (60000 milliseconds)
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            profileViewModel.getPhotosOfUsers(usernameObj)
            delay(6000)
        }
    }

    var selectedImageURI by remember {
        mutableStateOf<Uri?>(null)
    }

    val photoList by homeViewModel.photoList.collectAsState()

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageURI = uri
            var selectedImageURIEncoded = Uri.encode(selectedImageURI.toString());

            //Log.i("Picked Image--->", selectedImageURI.toString())
            navController.navigate("PHOTOEDITOR" + "/${selectedImageURIEncoded}");
            //uploadImage(selectedImageURI!!, homeViewModel, navController, username)

        })

    val tabItems = listOf(
        TabItem(
            title  = "Home",
            unselectedIcon = Icons.Outlined.Home,
            selectedIcon = Icons.Filled.Home
        ),

        TabItem(
            title  = "Profile",
            unselectedIcon = Icons.Outlined.Face,
            selectedIcon = Icons.Filled.Face
        )
        )

    var selectedTabIndex by remember{
        mutableStateOf(0)
    }

    if(selectedTabIndex == 0) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(250, 243, 224))
            .padding(top = 80.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(photoList) { imageInfo ->
            Card(
                onClick = {
                    val encodedUrl = Uri.encode(imageInfo.photoUrl)
                    navController.navigate("PHOTOVIEW/${encodedUrl}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 26.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(255, 251, 242, 255),
                    contentColor = Color(50, 50, 50, 255)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(12.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = "https://firebasestorage.googleapis.com/v0/b/androidsocialmediaapp-68d13.appspot.com/o/profilephoto.png?alt=media&token=276eab1a-0793-45c1-bdc5-a833d5306c1b",
                            contentDescription = "User profile photo",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = imageInfo.username,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.DarkGray
                        )
                    }

                    AsyncImage(
                        model = imageInfo.photoUrl,
                        contentDescription = "User uploaded image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.LightGray),
                        contentScale = ContentScale.Crop
                    )
                }
            }

        }
    }

        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp)
            ) {
                IconButton(
                    onClick = {
                        navController.navigate("PHOTOEDITOR/${"example_URI"}")
                    },
                    modifier = Modifier
                        .size(56.dp)
                        .shadow(8.dp, shape = CircleShape)
                        .background(Color(224, 122, 95), shape = CircleShape),
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Pick Photo",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }



    }

    else if(selectedTabIndex == 1){

        val userPhotoList by profileViewModel.userPhotoList.collectAsState()

        Column(
            Modifier
                .fillMaxSize()
                .background(Color(250, 243, 224)) // Soft cream background for a warm tone
                .padding(top = 80.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Username Text
            Text(
                modifier = Modifier.padding(20.dp),
                text = username,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                color = Color.DarkGray
            )

            // LazyVerticalGrid for displaying photos
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 4.dp, horizontal = 2.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(userPhotoList) { imageid ->
                    Card(
                        onClick = {
                            val encodedUrl = Uri.encode(imageid.photoUrl)
                            navController.navigate("PHOTOVIEW/${encodedUrl}")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .padding(2.dp)
                            .background(Color(255, 251, 242))
                            .shadow(2.dp, RoundedCornerShape(4.dp)),
                        shape = RoundedCornerShape(4.dp),
                    ) {
                        AsyncImage(
                            model = imageid.photoUrl,
                            contentDescription = "Image",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }

    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(250, 243, 224)), // Background color for TabRow
        contentColor = Color(224, 122, 95) // Content color for selected tab
    ) {
        tabItems.forEachIndexed{index, item->
            Tab(selected = index == selectedTabIndex, onClick = {
                selectedTabIndex = index
            },
                text = {
                    Text(text = item.title)
                },
                icon = {
                    Icon(imageVector = if(index == selectedTabIndex) item.selectedIcon else item.unselectedIcon, contentDescription = item.title)
                })
        }
    }

}

data class TabItem(

    val title:String,
    val unselectedIcon:ImageVector,
    val selectedIcon:ImageVector

)