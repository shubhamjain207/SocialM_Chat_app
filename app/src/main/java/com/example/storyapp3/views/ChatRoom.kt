package com.example.storyapp3.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.kotlin.foodclub.api.authentication.MessageSent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.storyapp3.viewmodels.ChatRoomViewModel
import com.example.storyapp3.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoom(navController: NavHostController, receiverUsername: String?){

    var viewModel: ChatRoomViewModel = viewModel()
    var username = ReadDataFromSharedPreferences(LocalContext.current)

    viewModel.getAllMessages(username,receiverUsername.toString())

    TopAppBar(title = { Text(text = receiverUsername.toString()) })

    var chatMessage by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 100.dp), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally){


            var messageList = viewModel.chatMessagesList.collectAsState().value

            LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp), modifier = Modifier.height(600.dp), state = listState){
                items(messageList){ index->
                if(index.sender == username){
                    Row (modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, end = 10.dp, start = 25.dp), horizontalArrangement = Arrangement.End){
                        Text(text = index.messagecontent,
                            Modifier
                                .padding(20.dp)
                                .background(Color(248, 173, 173, 255))
                                .wrapContentHeight(Alignment.Top, true)
                                .padding(10.dp).border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                                .clip(RoundedCornerShape(10.dp)))
                    }
                }
                 if(index.sender != username){
                     Row (modifier = Modifier
                         .fillMaxWidth()
                         .padding(top = 10.dp, end = 25.dp, start = 10.dp), horizontalArrangement = Arrangement.Start){
                         Text(text = index.messagecontent,
                             Modifier
                                 .padding(20.dp).
                                 background(Color(248, 173, 173, 255))
                                 .wrapContentHeight(Alignment.Top, true)
                                 .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                                 .clip(RoundedCornerShape(10.dp)))
                     }
                 }



                }
            }


        Row (modifier = Modifier
            .padding(start = 15.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)){
            TextField(
                value = chatMessage,
                onValueChange = {
                    chatMessage = it
                },
                modifier = Modifier
                    .background(Color(218, 218, 218, 1))
                    .padding(bottom = 30.dp)
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .width(300.dp)
                    .height(60.dp),

                placeholder = {
                    Text(text = "Enter message", color = Color(218, 218, 218, 228))
                },

                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(218, 218, 218, 128),
                    unfocusedBorderColor = Color(218, 218, 218, 110)
                )

            )

            Button(shape = CircleShape,
                modifier = Modifier
                    .clip(CircleShape)
                    .height(55.dp)
                    .width(55.dp)

                ,

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(156, 0, 0, 255),

                    ),
                contentPadding = PaddingValues(),
                onClick = {
                    var message = MessageSent(username.toString(),receiverUsername.toString(),chatMessage)

                    viewModel.sendMessage(message)
                    chatMessage=""
                }

            ) {

                  Text(text = ">")

            }
        }


        coroutineScope.launch {
            listState.scrollToItem(index = if(messageList.size!=0)messageList.size-1 else 0)
        }



    }



}



@Composable
fun ReadDataFromSharedPreferences(context: Context): String {
    val sharedPreferences = remember { context.getSharedPreferences("my_prefs", MODE_PRIVATE) }
    val value = sharedPreferences.getString("username", "default_value")
    return value.toString();

}