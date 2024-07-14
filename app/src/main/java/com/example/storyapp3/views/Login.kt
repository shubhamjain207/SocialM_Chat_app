package com.example.storyapp3.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.kotlin.foodclub.api.authentication.UserSignInInformation
import android.kotlin.foodclub.api.authentication.UserSignUpInformation
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.storyapp3.R
import com.example.storyapp3.viewmodels.LoginViewModel
import com.example.storyapp3.viewmodels.RegisterViewModel
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavHostController){


    var viewModel: LoginViewModel = viewModel()

    var context = LocalContext.current;


    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 40.dp, start = 30.dp, end = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(35.dp)
    ) {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Button(
                shape = RectangleShape,
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .width(20.dp)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
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
                text = "Login",
                fontSize = 32.sp,
                modifier = Modifier.padding(top = 10.dp)
            )

        }
        Column(

            Modifier
                .fillMaxSize()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            var userName by remember { mutableStateOf("") }
            var userPassword by remember { mutableStateOf("") }
            var error by remember { mutableStateOf("") }

            error = viewModel.errorMessage.collectAsState().value

            TextField(
                value = userName,
                onValueChange = {
                    userName = it;
                },
                modifier = Modifier
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(218, 218, 218, 70))
                    .fillMaxWidth(),

                placeholder = {
                    Text(
                        text = "Username",
                        color = Color(218, 218, 218, 238)
                    )
                },

                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(218, 218, 218, 158),
                    unfocusedBorderColor = Color(218, 218, 218, 140)
                )

            )

            TextField(
                
                value = userPassword,
                onValueChange = {
                    userPassword = it;
                },
                isError = if(viewModel.errorMessage.value == "") false else true,

                placeholder = {
                    Text(
                        text = "Password",
                        color = Color(218, 218, 218, 238)
                    )
                },

                modifier =
                Modifier
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(218, 218, 218, 70))
                    .fillMaxWidth(),


                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(218, 218, 218, 158),
                    unfocusedBorderColor = Color(218, 218, 218, 140)
                )


            )

            if(userPassword == ""){
                error = ""
            }

            if(error!="" && userPassword!=""){
                Text(text = error, color = Color.Red)
            }
            
            Button(
                shape = RectangleShape,
                modifier = Modifier
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(127, 127, 228, 255),

                    ), contentPadding = PaddingValues(15.dp),

                onClick = {
                    val user = UserSignInInformation(userName,userPassword)
                    viewModel.loginUser(user,navController)
                }

            ) {

                WriteDataToSharedPreferences(context,userName);

                Text(
                    color = Color.White,
                    text = "Log in",
                    fontWeight = FontWeight.Bold
                )
            }


            Row() {
                Text(
                    text = "",
                    fontSize = 11.sp,
                    color = Color.Red
                )
            }

            Row(
                modifier = Modifier.wrapContentWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Text(
                    color = Color.Black,
                    text = "New User?",
                    fontSize = 13.sp,
                    modifier = Modifier.padding(end = 5.dp)
                )
                ClickableText(
                    text = AnnotatedString("Register"),
                    onClick = {
                        navController.navigate("REGISTER")
                    },
                    style = TextStyle(
                        color = Color(127, 127, 228, 255),
                        fontSize = 13.sp,
                        textDecoration = TextDecoration.Underline
                    )
                )
            }


            Row(
                modifier = Modifier.wrapContentWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Text(
                    color = Color.Black,
                    text = "Forgot Password?",
                    fontSize = 13.sp,
                    modifier = Modifier.padding(end = 5.dp)
                )
                ClickableText(
                    text = AnnotatedString("Reset"),
                    onClick = {

                    },
                    style = TextStyle(
                        color = Color(127, 127, 228, 255),
                        fontSize = 13.sp,
                        textDecoration = TextDecoration.Underline
                    )
                )
            }


            }

        }

    }

@Composable
fun WriteDataToSharedPreferences(context: Context, newValue: String) {
    val sharedPreferences = remember { context.getSharedPreferences("my_prefs", MODE_PRIVATE) }
    val editor = sharedPreferences.edit()
    editor.putString("username", newValue)
    editor.apply()
}
