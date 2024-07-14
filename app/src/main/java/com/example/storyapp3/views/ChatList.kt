package com.example.storyapp3.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.kotlin.foodclub.api.authentication.PublicUser
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.storyapp3.R
import com.example.storyapp3.viewmodels.ChatListViewModel
import com.example.storyapp3.viewmodels.LoginViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ChatList(navController: NavHostController){

    var viewModel: ChatListViewModel = viewModel()

    viewModel.getUsers()


    ReadDataFromSharedPreferences(LocalContext.current)

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(105, 199, 241, 255)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(35.dp)
    ) {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Button(
                shape = RectangleShape,
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .width(30.dp)
                    .height(40.dp)
                    .background(Color(105, 199, 241, 255)),
                colors = ButtonDefaults.buttonColors(
                    containerColor =Color(105, 199, 241, 255),
                    contentColor = Color.White
                ), contentPadding = PaddingValues(5.dp),
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back_icon),
                    contentDescription = "Back",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                )
            }

            Text(
                text = "Chats",
                fontSize = 22.sp,
                modifier = Modifier.padding(top = 10.dp, start = 20.dp)
            )

        }
        var list:List<PublicUser> = viewModel.usersList.collectAsState().value

        Log.i("USERL LIST -->",list.toString())

        LazyColumn(verticalArrangement = Arrangement.spacedBy(15.dp), modifier = Modifier.padding(20.dp)){

            items(list){item->
                chatListItem(item,navController)
            }
        }
    }

}

@Composable
fun chatListItem(item: PublicUser, navController: NavHostController){

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .background(Color(156, 0, 0, 255))
        .clickable {

                        navController.navigate("CHATROOM/${item.username}")

                   },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically){
            Text(text = item.username)
        }


}

