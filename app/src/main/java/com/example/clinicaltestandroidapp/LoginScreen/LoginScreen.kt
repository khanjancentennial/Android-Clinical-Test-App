package com.example.clinicaltestandroidapp.LoginScreen

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(navController: NavController) {
    // Get the application context
    val context = LocalContext.current
    // Get SharedPreferences from the context
//    val sharedPreferences = context.getSharedPreferences("your_shared_prefs_name", Context.MODE_PRIVATE)
    // Initialize the ViewModel with the SharedPreferences
    val viewModel: LoginViewModel = viewModel()
    val isLoading by viewModel.isLoading.collectAsState()
    val loginSuccess by viewModel.loginSuccess.collectAsState()
    val showToast by viewModel.showToast.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    val passwordError by remember { mutableStateOf("") }


    println("loading : $isLoading")
    println("success : $loginSuccess")
    println("toast : $showToast")
    // If loginSuccess is true, navigate to HomeScreen

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            navController.navigate("homeScreen") // Trigger navigation
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        Text("Login!", fontSize = 32.sp, color = Color.Black)

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .height(50.dp),
            value = email,
            onValueChange = {email = it},
            placeholder = {
                Text(
                    text = "Email",
                    fontSize = 14.sp,
                    modifier = Modifier
                ) },
            shape = RoundedCornerShape(5.dp),
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                // display color based on value
//                containerColor = if (username == savedUsernameState.value && savedUsernameState.value!!.isNotEmpty()) lightBlue else lightGrey,
//                focusedBorderColor = if (username.isNotEmpty()) darkBlue else darkGrey,
//                unfocusedBorderColor = if (username.isNotEmpty()) darkBlue else darkGrey
//
//
//            )

        )

//        // Updated Email TextField
//        OutlinedTextField(
//            value = email,
//            onValueChange = { viewModel.onEmailChange(it) }, // You need to implement this in your ViewModel
//            label = { Text("Enter Email Id") }, // Wrap label with @Composable Text
//            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color(0xFFE0E0E0))
//        )

        if (email.isNullOrEmpty()) {
            Text(text = "Please Enter Email Id", color = Color.Red, fontSize = 12.sp) // Explicitly use `text =`
        }else{

        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .height(50.dp),
            value = password,
            onValueChange = { password = it},
            placeholder = {
                Text(
                    text = "Password",
                    fontSize = 14.sp,
                    modifier = Modifier
                ) },
            shape = RoundedCornerShape(5.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                // display color based on value
//                containerColor = if (username == savedUsernameState.value && savedUsernameState.value!!.isNotEmpty()) lightBlue else lightGrey,
//                focusedBorderColor = if (username.isNotEmpty()) darkBlue else darkGrey,
//                unfocusedBorderColor = if (username.isNotEmpty()) darkBlue else darkGrey
//
//
//            )

        )
//// Updated Password TextField
//        OutlinedTextField(
//            value = password,
//            onValueChange = { viewModel.onPasswordChange(it) }, // You need to implement this in your ViewModel
//            label = { Text("Enter Password") }, // Wrap label with @Composable Text
//            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
//            visualTransformation = PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color(0xFFE0E0E0))
//        )

        if (password.isNullOrEmpty()) {
            Text(text = "Please Enter Password", color = Color.Red, fontSize = 12.sp) // Explicitly use `text =`
        }

        Spacer(modifier = Modifier.height(50.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    viewModel.login(context, email, password)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("New User? ")
            TextButton(onClick = { navController.navigate("registrationScreen") }) {
                Text("Register Here")
            }
        }

        // Toast Message
        if (showToast) {
            Toast.makeText(
                context,
                if (loginSuccess.toString() == "true") "Login successful!" else "Please check your credentials!",
                Toast.LENGTH_SHORT
            ).show()
            viewModel.dismissToast()
        }
    }
}
