package com.example.storyapp3

import android.kotlin.foodclub.navigation.graphs.RootNavigationGraph
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.storyapp3.ui.theme.StoryApp3Theme
import com.example.storyapp3.views.Login
import com.example.storyapp3.views.Register

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoryApp3Theme {
                val navController = rememberNavController()
                RootNavigationGraph(navController)
            }
        }
    }
}

