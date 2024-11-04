package com.example.clinicaltestandroidapp.HomeScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
//    val loginViewModel: LoginViewModel = viewModel()
    val homeScreenViewModel: HomeScreenViewModel = viewModel()
    val deleteViewModel: DeletePatientViewModel = viewModel()

    var searchText by remember { mutableStateOf("") }
    var isEditActive by remember { mutableStateOf(false) }
    var isDeleteActive by remember { mutableStateOf(false) }
    var isDetailsActive by remember { mutableStateOf(false) }
    var selectedPatient: PatientData? by remember { mutableStateOf(null) }
    var logoutTriggered by remember { mutableStateOf(false) }


    LaunchedEffect(logoutTriggered) {
        if (logoutTriggered) {
            navController.navigate("loginScreen")
        }
    }


    LaunchedEffect(Unit) {
        homeScreenViewModel.fetchPatientData()
//        loginViewModel.loginSuccess = true
    }
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF880808))
                .padding(16.dp)
        ) {
            HeaderView(
                searchText = searchText,
                onSearchTextChange = { searchText = it },
                onLogoutClick = {
//                    loginViewModel.logOut()
//                    if (!loginViewModel.loginSuccess) {
                        logoutTriggered = true
//                    }
                },
                onAddPatientClick = { navController.navigate("addPatientDetailsScreen") }
            )

            if (homeScreenViewModel.patients.value.isEmpty()) {
                Text(text = "No patients",
                    color = Color.White
                    )

            }
            else {
                PatientListView(
                    patients = homeScreenViewModel.patients.value,
                    onEditClick = { patient ->
                        selectedPatient = patient
                        isEditActive = true
                        navController.navigate("editPatientDetailsScreen/${patient.id}/${patient.firstName}/${patient.lastName}/${patient.phoneNumber}/${patient.email}/${patient.height}/${patient.weight}/${patient.address}/${if (patient.gender == 0) "0" else "1"}")
                    },
                    onDeleteClick = { patient ->
                        selectedPatient = patient
                        isDeleteActive = true
                    },
                    onDetailsClick = { patient ->
                        selectedPatient = patient
                        isDetailsActive = true
                        navController.navigate("clinicalTestsDetailsScreen/${patient.id}/${patient.firstName}/${patient.lastName}")
                    }
                )
            }

            if (isDeleteActive) {
                DeleteConfirmationDialog(
                    patient = selectedPatient,
                    onDeleteConfirmed = {
                        selectedPatient?.let { patient ->
                            deleteViewModel.deletePatient(patient.id) { success ->
                                if (success) {
                                    homeScreenViewModel.fetchPatientData()
                                }
                            }
                        }
                        isDeleteActive = false
                    },
                    onDismiss = { isDeleteActive = false }
                )
            }
        }
    }
}

@Composable
fun HeaderView(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onLogoutClick: () -> Unit,
    onAddPatientClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            BasicTextField(
//                value = searchText,
//                onValueChange = onSearchTextChange,
//                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
//                keyboardActions = KeyboardActions(onSearch = {
//
//                }),
//                modifier = Modifier
//                    .weight(1f)
//                    .background(Color.White, shape = MaterialTheme.shapes.medium)
//                    .padding(8.dp)
//            )
            IconButton(onClick = onLogoutClick) {
                Icon(Icons.Default.ExitToApp, contentDescription = null, tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("All Patients", color = Color.White, fontSize = 24.sp)
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = onAddPatientClick) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add")
            }
        }
    }
}

@Composable
fun PatientListView(
    patients: List<PatientData>,
    onEditClick: (PatientData) -> Unit,
    onDeleteClick: (PatientData) -> Unit,
    onDetailsClick: (PatientData) -> Unit
) {
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .background(Color.White)
        .border(
            border = BorderStroke(1.dp, Color.White),
            shape = RoundedCornerShape(50),
        )
        .fillMaxHeight()
    ) {
        patients.forEach { patient ->
            PatientRow(
                patient = patient,
                onEditClick = { onEditClick(patient) },
                onDeleteClick = { onDeleteClick(patient) },
                onDetailsClick = { onDetailsClick(patient) }
            )
            Divider()
        }
    }
}

@Composable
fun PatientRow(
    patient: PatientData,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDetailsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("${patient.firstName} ${patient.lastName}", fontSize = 18.sp)
            Text("Height: ${patient.height}")
            Text("Weight: ${patient.weight}")
            Text("Gender: ${if (patient.gender == 0) "Male" else "Female"}")
            Text(
                "Status: ${patient.status}",
                color = if (patient.status == "critical") Color.Red else Color.Green
            )
        }
        IconButton(onClick = onEditClick) {
            Icon(Icons.Default.Edit, contentDescription = null, tint = Color.Green)
        }
        IconButton(onClick = onDeleteClick) {
            Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
        }
        IconButton(onClick = onDetailsClick) {
            Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Color.Blue)
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    patient: PatientData?,
    onDeleteConfirmed: () -> Unit,
    onDismiss: () -> Unit
) {
    if (patient != null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Delete Patient") },
            text = { Text("Are you sure you want to delete ${patient.firstName} ${patient.lastName}?") },
            confirmButton = {
                TextButton(onClick = onDeleteConfirmed) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}
