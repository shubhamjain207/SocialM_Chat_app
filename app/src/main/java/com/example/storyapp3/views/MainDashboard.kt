package com.example.storyapp3.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.storyapp3.R

val sampleImageIds = listOf(
    R.drawable.torchblog,
    R.drawable.torchblog1,
    R.drawable.torchblog2

)


@Composable
fun MainDashboard(navController: NavHostController){
    
    LazyColumn(modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(sampleImageIds){ imageid->
            Image(painter = painterResource(id = imageid), contentDescription = "back")
        }

    }
    
    
    
    
}