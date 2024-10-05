package com.example.storyapp3

import android.kotlin.foodclub.navigation.graphs.RootNavigationGraph
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoryApp3Theme {


                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("MainActivity", "Fetching FCM registration token failed", task.exception)
                        return@addOnCompleteListener
                    }

                    // Get new FCM registration token
                    val token = task.result

                    // Log the token or send it to your server
                    Log.d("MainActivity", "Token: $token")
                }

                val navController = rememberNavController()
                RootNavigationGraph(navController)
            }
        }
    }
}

