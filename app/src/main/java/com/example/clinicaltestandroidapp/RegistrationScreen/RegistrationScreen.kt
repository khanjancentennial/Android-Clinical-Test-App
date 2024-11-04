package com.example.clinicaltestandroidapp.RegistrationScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
@Composable
fun RegistrationScreen(navController: NavController) {
    // Get the application context
    val context = LocalContext.current
    // Get SharedPreferences from the context
//    val sharedPreferences = context.getSharedPreferences("your_shared_prefs_name", Context.MODE_PRIVATE)
    // Initialize the ViewModel with the SharedPreferences
    val viewModel: RegistrationViewModel = viewModel()


    val keyboardController = LocalSoftwareKeyboardController.current
    var fName by remember { mutableStateOf("") }
    var lName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    val passwordError by remember { mutableStateOf("") }
    val isLoading by viewModel.isLoading.collectAsState()
    val registrationSuccess by viewModel.registerSuccess.collectAsState()
    val showToast by viewModel.showToast.collectAsState()
    var gender by remember { mutableStateOf("Male") }
    var healthcareProvider by remember { mutableStateOf("Doctor") }
    val scrollState = rememberScrollState()


    LaunchedEffect(registrationSuccess) {
        if (registrationSuccess) {
            navController.navigate("loginScreen") // Navigate to HomeScreen
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        Text("Register!", fontSize = 32.sp, color = Color.Black)

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .height(50.dp),
            value = fName,
            onValueChange = {fName = it},
            placeholder = {
                Text(
                    text = "First Name",
                    fontSize = 14.sp,
                    modifier = Modifier
                ) },
            shape = RoundedCornerShape(5.dp),

        )

        if (fName.isNullOrEmpty()) {
            Text(text = "Please Enter First Name", color = Color.Red, fontSize = 12.sp) // Explicitly use `text =`
        }else{

        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .height(50.dp),
            value = lName,
            onValueChange = {lName = it},
            placeholder = {
                Text(
                    text = "Last Name",
                    fontSize = 14.sp,
                    modifier = Modifier
                ) },
            shape = RoundedCornerShape(5.dp),

            )

        if (lName.isNullOrEmpty()) {
            Text(text = "Please Enter Last Name", color = Color.Red, fontSize = 12.sp) // Explicitly use `text =`
        }else{

        }

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

        )

        if (email.isNullOrEmpty()) {
            Text(text = "Please Enter Email Id", color = Color.Red, fontSize = 12.sp) // Explicitly use `text =`
        }else{

        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .height(50.dp),
            value = phoneNumber,
            onValueChange = {phoneNumber = it},
            placeholder = {
                Text(
                    text = "phone Number",
                    fontSize = 14.sp,
                    modifier = Modifier
                ) },
            shape = RoundedCornerShape(5.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

            )

        if (phoneNumber.isNullOrEmpty()) {
            Text(text = "Please Enter Phone Number", color = Color.Red, fontSize = 12.sp) // Explicitly use `text =`
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

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .height(50.dp),
            value = address,
            onValueChange = {address = it},
            placeholder = {
                Text(
                    text = "Address",
                    fontSize = 14.sp,
                    modifier = Modifier
                ) },
            shape = RoundedCornerShape(5.dp),

        )

        if (address.isNullOrEmpty()) {
            Text(text = "Please Enter Address", color = Color.Red, fontSize = 12.sp) // Explicitly use `text =`
        }else{

        }
        Spacer(modifier = Modifier.height(20.dp))
        Text("Gender", fontSize = 16.sp, color = Color.Black)
        Row {
            listOf("Male", "Female").forEach { genderOption ->
                Row(
                    modifier = Modifier
                        .selectable(
                            selected = (gender == genderOption),
                            onClick = { gender = genderOption },
                            role = Role.RadioButton
                        )
                        .padding(end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (gender == genderOption),
                        onClick = { gender = genderOption }
                    )
                    Text(text = genderOption, fontSize = 14.sp)
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text("Healthcare Provider", fontSize = 16.sp, color = Color.Black)
        Row {
            listOf("Doctor", "Nurse").forEach { healthcareOption ->
                Row(
                    modifier = Modifier
                        .selectable(
                            selected = (healthcareProvider == healthcareOption),
                            onClick = { healthcareProvider = healthcareOption },
                            role = Role.RadioButton
                        )
                        .padding(end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (healthcareProvider == healthcareOption),
                        onClick = { healthcareProvider = healthcareOption }
                    )
                    Text(text = healthcareOption, fontSize = 14.sp)
                }
            }
        }


        Spacer(modifier = Modifier.height(50.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    viewModel.register(
                        context,
                        email,
                        password,
                        fName,
                        lName,
                        phoneNumber,
                        address,
                        gender,
                        healthcareProvider
                        ) // Call login function
                    keyboardController?.hide() // Hide the keyboard
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Existing User? ")
            TextButton(onClick = { navController.navigate("loginScreen") }) {
                Text("Login Here")
            }
        }

        // Toast Message
        if (showToast) {
            LaunchedEffect(Unit) {
                Toast.makeText(
                    context,
                    if (registrationSuccess) "Registration successful!" else "Please check your Details!",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.dismissToast() // Dismiss the toast after showing
            }
        }
    }
}