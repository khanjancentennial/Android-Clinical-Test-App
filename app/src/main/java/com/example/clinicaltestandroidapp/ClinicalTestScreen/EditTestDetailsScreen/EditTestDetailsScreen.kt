package com.example.clinicaltestandroidapp.ClinicalTestScreen.EditTestDetailsScreen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditTestDetailsScreen(navController: NavController,
                             patientIdReceived: String?,
                             fNameReceived: String?,
                             lNameReceived: String?,
                             bloodPressureReceived: String?,
                             respiratoryRateReceived: String?,
                             bloodOxygenLevelReceived: String?,
                             heartbeatRateReceived: String?,
                             chiefComplaintReceived: String?,
                             pastMedicalHistoryReceived: String?,
                             medicalDiagnosisReceived: String?,
                             medicalPrescriptionReceived : String?,
                             testIdReceived : String?

                             ) {
    // Get the application context
    val context = LocalContext.current
    // Get SharedPreferences from the context
//    val sharedPreferences = context.getSharedPreferences("your_shared_prefs_name", Context.MODE_PRIVATE)
    // Initialize the ViewModel with the SharedPreferences
    val viewModel: EditTestDetailsViewModel = viewModel()


    val keyboardController = LocalSoftwareKeyboardController.current
    var patientId by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var bloodPressure by remember { mutableStateOf("") }
    var respiratoryRate by remember { mutableStateOf("") }
    var bloodOxygenLevel by remember { mutableStateOf("") }
    var heartbeatRate by remember { mutableStateOf("") }
    var chiefComplaint by remember { mutableStateOf("") }
    var pastMedicalHistory by remember { mutableStateOf("") }
    var medicalDiagnosis by remember { mutableStateOf("") }
    var medicalPrescription by remember { mutableStateOf("") }
    var testId by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.collectAsState()
    val editPatientSuccess by viewModel.editPatientSuccess.collectAsState()
    val showToast by viewModel.showToast.collectAsState()
    val scrollState = rememberScrollState()

    // Initialize values once using LaunchedEffect
    LaunchedEffect(Unit) {
        patientId = patientIdReceived.orEmpty()
        firstName = fNameReceived.orEmpty()
        lastName = lNameReceived.orEmpty()
        bloodPressure = bloodPressureReceived.toString()
        respiratoryRate = respiratoryRateReceived.toString()
        bloodOxygenLevel = bloodOxygenLevelReceived.toString()
        heartbeatRate = heartbeatRateReceived.toString()
        chiefComplaint = chiefComplaintReceived.toString()
        pastMedicalHistory = pastMedicalHistoryReceived.toString()
        medicalDiagnosis = medicalDiagnosisReceived.toString()
        medicalPrescription = medicalPrescriptionReceived.toString()
        testId = testIdReceived.toString()

    }
    LaunchedEffect(editPatientSuccess) {
        if (editPatientSuccess) {
            navController.navigate("clinicalTestsDetailsScreen/${patientId}/${firstName}/${lastName}"){
                popUpTo(navController.previousBackStackEntry?.destination?.id ?: navController.graph.startDestinationId) {
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
            Text("Edit Test Details!", fontSize = 32.sp, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .height(50.dp),
            value = bloodPressure,
            onValueChange = {bloodPressure = it},
            placeholder = {
                Text(
                    text = "Blood Pressure",
                    fontSize = 14.sp,
                    modifier = Modifier
                ) },
            shape = RoundedCornerShape(5.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

        )

        if (bloodPressure.isEmpty()) {
            Text(text = "Please Enter Blood Pressure", color = Color.Red, fontSize = 12.sp) // Explicitly use `text =`
        }else{

        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .height(50.dp),
            value = respiratoryRate,
            onValueChange = {respiratoryRate = it},
            placeholder = {
                Text(
                    text = "Respiratory Rate",
                    fontSize = 14.sp,
                    modifier = Modifier
                ) },
            shape = RoundedCornerShape(5.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

        )

        if (respiratoryRate.isEmpty()) {
            Text(text = "Please Enter Respiratory Rate", color = Color.Red, fontSize = 12.sp) // Explicitly use `text =`
        }else{

        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .height(50.dp),
            value = bloodOxygenLevel,
            onValueChange = {bloodOxygenLevel = it},
            placeholder = {
                Text(
                    text = "Blood Oxygen Level",
                    fontSize = 14.sp,
                    modifier = Modifier
                ) },
            shape = RoundedCornerShape(5.dp),

            )

        if (bloodOxygenLevel.isNullOrEmpty()) {
            Text(text = "Please Enter Blood Oxygen Level", color = Color.Red, fontSize = 12.sp) // Explicitly use `text =`
        }else{

        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .height(50.dp),
            value = heartbeatRate,
            onValueChange = {heartbeatRate = it},
            placeholder = {
                Text(
                    text = "Heart Beat Rate",
                    fontSize = 14.sp,
                    modifier = Modifier
                ) },
            shape = RoundedCornerShape(5.dp),

            )

        if (heartbeatRate.isNullOrEmpty()) {
            Text(text = "Please Enter Heart Beat Rate", color = Color.Red, fontSize = 12.sp) // Explicitly use `text =`
        }else{

        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .height(50.dp),
            value = chiefComplaint,
            maxLines = 3,
            minLines = 3,
            onValueChange = {chiefComplaint = it},
            placeholder = {
                Text(
                    text = "Chief Complaint",
                    fontSize = 14.sp,
                    modifier = Modifier
                ) },
            shape = RoundedCornerShape(5.dp),

            )

        if (chiefComplaint.isNullOrEmpty()) {
            Text(text = "Please Enter Chief Complaint", color = Color.Red, fontSize = 12.sp) // Explicitly use `text =`
        }else{

        }


        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .height(50.dp),
            value = pastMedicalHistory,
            maxLines = 3,
            minLines = 3,
            onValueChange = {pastMedicalHistory = it},
            placeholder = {
                Text(
                    text = "Past Medical History",
                    fontSize = 14.sp,
                    modifier = Modifier
                ) },
            shape = RoundedCornerShape(5.dp),

            )

        if (pastMedicalHistory.isNullOrEmpty()) {
            Text(text = "Please Enter Past Medical History", color = Color.Red, fontSize = 12.sp) // Explicitly use `text =`
        }else{

        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .height(50.dp),
            value = medicalDiagnosis,
            maxLines = 3,
            minLines = 3,
            onValueChange = {medicalDiagnosis = it},
            placeholder = {
                Text(
                    text = "Medical Diagnosis",
                    fontSize = 14.sp,
                    modifier = Modifier
                ) },
            shape = RoundedCornerShape(5.dp),

            )

        if (medicalDiagnosis.isNullOrEmpty()) {
            Text(text = "Please Enter Medical Diagnosis", color = Color.Red, fontSize = 12.sp) // Explicitly use `text =`
        }else{

        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .height(50.dp),
            value = medicalPrescription,
            maxLines = 3,
            minLines = 3,
            onValueChange = {medicalPrescription = it},
            placeholder = {
                Text(
                    text = "Medical Prescription",
                    fontSize = 14.sp,
                    modifier = Modifier
                ) },
            shape = RoundedCornerShape(5.dp),

            )

        if (medicalPrescription.isNullOrEmpty()) {
            Text(text = "Please Enter Medical Prescription", color = Color.Red, fontSize = 12.sp) // Explicitly use `text =`
        }else{

        }

        Spacer(modifier = Modifier.height(50.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    viewModel.editPatient(
                        context,
                        bloodOxygenLevel.toInt(),
                        bloodPressure.toInt(),
                        respiratoryRate.toInt(),
                        heartbeatRate.toInt(),
                        chiefComplaint,
                        pastMedicalHistory,
                        medicalDiagnosis,
                        medicalPrescription,
                        patientId,
                        testId
                    ) // Call login function
                    keyboardController?.hide() // Hide the keyboard
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Edit Test Details", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))



        // Toast Message
        if (showToast) {
            LaunchedEffect(Unit) {
                Toast.makeText(
                    context,
                    if (editPatientSuccess) "Patient test details edited successful!" else "Please check Details!",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.dismissToast() // Dismiss the toast after showing
            }
        }
    }
}