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
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
            .padding(top = 100.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(photoList) { imageInfo ->
            Card(
                onClick = {
                            val encodedUrl = Uri.encode(imageInfo.photoUrl)
                            navController.navigate("PHOTOVIEW" + "/${encodedUrl}")
                          },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    Row (modifier = Modifier.fillMaxWidth()){

                        AsyncImage(
                            alignment = Alignment.Center,
                            model = "https://firebasestorage.googleapis.com/v0/b/androidsocialmediaapp-68d13.appspot.com/o/profilephoto.png?alt=media&token=276eab1a-0793-45c1-bdc5-a833d5306c1b",
                            contentDescription = "Image",
                            modifier = Modifier
                                .size(30.dp)
                                .aspectRatio(1f) // Keep aspect ratio for images
                                .clip(RoundedCornerShape(20.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = imageInfo.username,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(8.dp)
                        )
                    }


                    AsyncImage(
                        model = imageInfo.photoUrl,
                        contentDescription = "Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f) // Keep aspect ratio for images
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Fit
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
            Button(
                onClick = {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            ) {
                Text(text = "Pick Photo")
            }
        }
    }

    }

    else if(selectedTabIndex == 1){

        val userPhotoList by profileViewModel.userPhotoList.collectAsState()

        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = 100.dp, start = 30.dp, end = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(35.dp)
        ) {

            Text(username);
            LazyVerticalGrid(
                columns = GridCells.Fixed(3), // 2 columns in the grid
                modifier = Modifier.fillMaxSize(),
                content = {
                    items(userPhotoList) { imageid -> // Example: 20 items in the grid
                        AsyncImage(model = imageid.photoUrl,
                            contentDescription ="Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(150.dp)
                                .padding(vertical = 10.dp),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            )
        }
    }

    TabRow(selectedTabIndex = selectedTabIndex) {
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