package com.example.clinicaltestandroidapp.ClinicalTestScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun ClinicalTestScreen(navController: NavController,
                       patientIdReceived : String?,
                       fNameReceived : String?,
                       lNameReceived : String?

) {
//    val loginViewModel: LoginViewModel = viewModel()
    val clinicalTestScreenViewModel: ClinicalTestViewModel = viewModel()
    val deleteViewModel: DeleteClinicalTestViewModel = viewModel()

    var searchText by remember { mutableStateOf("") }
    var patientId by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var isEditActive by remember { mutableStateOf(false) }
    var isDeleteActive by remember { mutableStateOf(false) }
    var isDetailsActive by remember { mutableStateOf(false) }
    var selectedPatient: PatientRecord? by remember { mutableStateOf(null) }
    var logoutTriggered by remember { mutableStateOf(false) }


    // Initialize values once using LaunchedEffect
    LaunchedEffect(Unit) {
        patientId = patientIdReceived.toString()
        firstName = fNameReceived.toString()
        lastName = lNameReceived.toString()
    }



    LaunchedEffect(Unit) {
        clinicalTestScreenViewModel.fetchPatientData()
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
                onAddPatientClick = { navController.navigate("addTestDetailsScreen/${patientId}/${firstName}/${lastName}") },
                navController = navController
            )

            if (clinicalTestScreenViewModel.patients.value.isEmpty()) {
                Text(text = "No patients",
                    color = Color.White
                )

            }
            else {
                PatientListView(
                    patients = clinicalTestScreenViewModel.patients.value,
                    onEditClick = { patient ->
                        selectedPatient = patient
                        isEditActive = true
                        navController.navigate("editClinicalTestScreen/${patientId}/${firstName}/${lastName}/${patient.bloodPressure}/${patient.respiratoryRate}/${patient.heartbeatRate}/${patient.bloodOxygenLevel}/${patient.chiefComplaint}/${patient.pastMedicalHistory}/${patient.medicalDiagnosis}/${patient.medicalPrescription}/${patient._id}")
                    },
                    onDeleteClick = { patient ->
                        selectedPatient = patient
                        isDeleteActive = true
                    },
                    patientId = patientId
                )
            }

            if (isDeleteActive) {
                DeleteConfirmationDialog(
                    patient = selectedPatient,
                    onDeleteConfirmed = {
                        selectedPatient?.let { patient ->
                            deleteViewModel.deletePatient(patient._id) { success ->
                                if (success) {
                                    clinicalTestScreenViewModel.fetchPatientData()
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
    onAddPatientClick: () -> Unit,
    navController : NavController
) {
    Column {


        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
            Spacer(modifier = Modifier.width(8.dp))
            Text("All Tests", color = Color.White, fontSize = 24.sp)
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
    patients: List<PatientRecord>,
    onEditClick: (PatientRecord) -> Unit,
    onDeleteClick: (PatientRecord) -> Unit,
    patientId: String

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
                patientId = patientId
            )
            Divider()
        }
    }
}

@Composable
fun PatientRow(
    patient: PatientRecord,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    patientId : String,
) {
    val records = remember { mutableStateListOf<PatientRecord>() }
    records.clear()
    if(patient.patient._id == patientId) {
        records.add(patient)
    }
    if(records.isEmpty()){
        Text(text = "No Data")
    }else{
        records.forEach { patient ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("${patient.creationDateTime}", fontSize = 18.sp)
                    Text("Blood Pressure: ${patient.bloodPressure}")
                    Text("Blood Oxygen Level: ${patient.bloodOxygenLevel}")
                    Text("Respiratory Rate: ${patient.respiratoryRate}")
                    Text("Heart beat Rate: ${patient.heartbeatRate}")
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

            }
        }
    }


}

@Composable
fun DeleteConfirmationDialog(
    patient: PatientRecord?,
    onDeleteConfirmed: () -> Unit,
    onDismiss: () -> Unit
) {
    if (patient != null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Delete Test Record") },
            text = { Text("Are you sure you want to delete test record of ${patient.patient.firstName} ${patient.patient.lastName}?") },
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