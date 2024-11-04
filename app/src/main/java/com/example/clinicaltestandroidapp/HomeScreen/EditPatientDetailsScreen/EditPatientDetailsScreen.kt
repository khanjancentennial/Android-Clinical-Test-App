package com.example.clinicaltestandroidapp.HomeScreen.EditPatientDetailsScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import com.example.clinicaltestandroidapp.HomeScreen.AddPatientDetailsScreen.EditPatientDetailsViewModel

@Composable
fun EditPatientDetailsScreen(navController: NavController,
                             patientIdReceived: String?,
                             fNameReceived: String?,
                             lNameReceived: String?,
                             phoneNumberReceived: String?,
                             addressReceived: String?,
                             heightReceived: String?,
                             weightReceived: String?,
                             genderReceived: String?,
                             emailReceived: String?

                             ) {
    // Get the application context
    val context = LocalContext.current
    // Get SharedPreferences from the context
//    val sharedPreferences = context.getSharedPreferences("your_shared_prefs_name", Context.MODE_PRIVATE)
    // Initialize the ViewModel with the SharedPreferences
    val viewModel: EditPatientDetailsViewModel = viewModel()


    val keyboardController = LocalSoftwareKeyboardController.current
    var fName by remember { mutableStateOf("") }
    var lName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var patientId by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.collectAsState()
    val editPatientSuccess by viewModel.editPatientSuccess.collectAsState()
    val showToast by viewModel.showToast.collectAsState()
    var gender by remember { mutableStateOf("Male") }
    val scrollState = rememberScrollState()

    // Initialize values once using LaunchedEffect
    LaunchedEffect(Unit) {
        patientId = patientIdReceived.orEmpty()
        fName = fNameReceived.orEmpty()
        lName = lNameReceived.orEmpty()
        phoneNumber = phoneNumberReceived.orEmpty()
        address = addressReceived.orEmpty()
        height = heightReceived.orEmpty()
        weight = weightReceived.orEmpty()
        email = emailReceived.orEmpty()
        gender = if (genderReceived == "0") "Male" else "Female"
    }
    LaunchedEffect(editPatientSuccess) {
        if (editPatientSuccess) {
            navController.navigate("HomeScreen"){
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            } // Navigate to HomeScreen
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
        // Back Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Red
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("Edit Patient!", fontSize = 32.sp, color = Color.Black)
        }

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
            value = address,
            maxLines = 3,
            minLines = 3,
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

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .height(50.dp),
            value = weight,
            onValueChange = { weight = it},
            placeholder = {
                Text(
                    text = "Weight",
                    fontSize = 14.sp,
                    modifier = Modifier
                ) },
            shape = RoundedCornerShape(5.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        if (weight.isNullOrEmpty()) {
            Text(text = "Please Enter weight", color = Color.Red, fontSize = 12.sp) // Explicitly use `text =`
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .height(50.dp),
            value = height,
            onValueChange = { height = it},
            placeholder = {
                Text(
                    text = "height",
                    fontSize = 14.sp,
                    modifier = Modifier
                ) },
            shape = RoundedCornerShape(5.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        if (weight.isNullOrEmpty()) {
            Text(text = "Please Enter Height", color = Color.Red, fontSize = 12.sp) // Explicitly use `text =`
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

        Spacer(modifier = Modifier.height(50.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    viewModel.editPatient(
                        context,
                        email,
                        fName,
                        lName,
                        phoneNumber,
                        address,
                        gender,
                        height,
                        weight,
                        patientId
                    ) // Call login function
                    keyboardController?.hide() // Hide the keyboard
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Edit Patient Details", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))



        // Toast Message
        if (showToast) {
            LaunchedEffect(Unit) {
                Toast.makeText(
                    context,
                    if (editPatientSuccess) "Patient add successful!" else "Please check Details!",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.dismissToast() // Dismiss the toast after showing
            }
        }
    }
}