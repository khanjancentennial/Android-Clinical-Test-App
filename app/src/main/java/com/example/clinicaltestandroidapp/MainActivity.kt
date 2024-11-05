package com.example.clinicaltestandroidapp;

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.clinicaltestandroidapp.ClinicalTestScreen.AddTestDetailsScreen.AddTestDetailsScreen
import com.example.clinicaltestandroidapp.ClinicalTestScreen.ClinicalTestScreen
import com.example.clinicaltestandroidapp.HomeScreen.HomeScreen
import com.example.clinicaltestandroidapp.ClinicalTestScreen.ClinicalTestScreen
import com.example.clinicaltestandroidapp.ClinicalTestScreen.EditTestDetailsScreen.EditTestDetailsScreen
import com.example.clinicaltestandroidapp.HomeScreen.AddPatientDetailsScreen
import com.example.clinicaltestandroidapp.HomeScreen.EditPatientDetailsScreen.EditPatientDetailsScreen
import com.example.clinicaltestandroidapp.LoginScreen.LoginScreen
import com.example.clinicaltestandroidapp.RegistrationScreen.RegistrationScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainApp() // Call your main app structure
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainApp() {
    // Create a NavController
    val navController: NavHostController = rememberNavController()

    // Setup NavHost to manage navigation between screens
    NavHost(navController = navController, startDestination = "loginScreen") {
        composable("loginScreen") {
            LoginScreen(navController = navController)
        }
//         Uncomment and implement other screens as needed
         composable("homeScreen") {
             HomeScreen(navController = navController) // Your HomeScreen Composable
         }
         composable("registrationScreen") {
             RegistrationScreen(navController = navController) // Your RegistrationScreen Composable
         }
        composable("addPatientDetailsScreen") {
            AddPatientDetailsScreen(navController = navController) // Your RegistrationScreen Composable
         }

        composable(
            route = "addTestDetailsScreen/{patientId}/{firstName}/{lastName}",
            arguments = listOf(
                navArgument("patientId") { type = NavType.StringType },
                navArgument("firstName") { type = NavType.StringType },
                navArgument("lastName") { type = NavType.StringType }

                )
        ) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId")
            val firstName = backStackEntry.arguments?.getString("firstName")
            val lastName = backStackEntry.arguments?.getString("lastName")

            AddTestDetailsScreen(
                navController = navController,
                patientIdReceived = patientId,
                fNameReceived = firstName,
                lNameReceived = patientId
            )
        }
//        composable("editPatientDetailsScreen") {
//            EditPatientDetailsScreen(navController = navController) // Your RegistrationScreen Composable
//         }

        composable(
            route = "editPatientDetailsScreen/{patientId}/{firstName}/{lastName}/{phoneNumber}/{email}/{height}/{weight}/{address}/{gender}",
            arguments = listOf(
                navArgument("patientId") { type = NavType.StringType },
                navArgument("firstName") { type = NavType.StringType },
                navArgument("lastName") { type = NavType.StringType },
                navArgument("phoneNumber") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType },
                navArgument("height") { type = NavType.StringType },
                navArgument("weight") { type = NavType.StringType },
                navArgument("address") { type = NavType.StringType },
                navArgument("gender") { type = NavType.StringType }

            )
        ) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId")
            val firstName = backStackEntry.arguments?.getString("firstName")
            val lastName = backStackEntry.arguments?.getString("lastName")
            val phoneNumber = backStackEntry.arguments?.getString("phoneNumber")
            val email = backStackEntry.arguments?.getString("email")
            val height = backStackEntry.arguments?.getString("height")
            val weight = backStackEntry.arguments?.getString("weight")
            val address = backStackEntry.arguments?.getString("address")
            val gender = backStackEntry.arguments?.getString("gender")
            EditPatientDetailsScreen(
                navController = navController,
                patientIdReceived = patientId,
                fNameReceived = firstName,
                lNameReceived = lastName,
                phoneNumberReceived = phoneNumber,
                emailReceived = email,
                heightReceived = height,
                weightReceived = weight,
                addressReceived = address,
                genderReceived = gender
            )
        }

        composable(
            route = "clinicalTestsDetailsScreen/{patientId}/{firstName}/{lastName}",
            arguments = listOf(
                navArgument("patientId") { type = NavType.StringType },
                navArgument("firstName") { type = NavType.StringType },
                navArgument("lastName") { type = NavType.StringType },

            )
        ) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId")
            val firstName = backStackEntry.arguments?.getString("firstName")
            val lastName = backStackEntry.arguments?.getString("lastName")

            ClinicalTestScreen(
                navController = navController,
                patientIdReceived = patientId,
                fNameReceived = firstName,
                lNameReceived = lastName
            )
        }

        composable(
            route = "editClinicalTestScreen/{patientId}/{firstName}/{lastName}/{bloodPressure}/{respiratoryRate}/{heartbeatRate}/{bloodOxygenLevel}/{chiefComplaint}/{pastMedicalHistory}/{medicalDiagnosis}/{medicalPrescription}/{testId}",
            arguments = listOf(
                navArgument("patientId") { type = NavType.StringType },
                navArgument("firstName") { type = NavType.StringType },
                navArgument("lastName") { type = NavType.StringType },
                navArgument("bloodPressure") { type = NavType.StringType },
                navArgument("respiratoryRate") { type = NavType.StringType },
                navArgument("heartbeatRate") { type = NavType.StringType },
                navArgument("bloodOxygenLevel") { type = NavType.StringType },
                navArgument("chiefComplaint") { type = NavType.StringType },
                navArgument("pastMedicalHistory") { type = NavType.StringType },
                navArgument("medicalDiagnosis") { type = NavType.StringType },
                navArgument("medicalPrescription") { type = NavType.StringType },
                navArgument("testId") { type = NavType.StringType },

            )
        ) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId")
            val firstName = backStackEntry.arguments?.getString("firstName")
            val lastName = backStackEntry.arguments?.getString("lastName")
            val bloodPressure = backStackEntry.arguments?.getString("bloodPressure")
            val respiratoryRate = backStackEntry.arguments?.getString("respiratoryRate")
            val heartbeatRate = backStackEntry.arguments?.getString("heartbeatRate")
            val bloodOxygenLevel = backStackEntry.arguments?.getString("bloodOxygenLevel")
            val chiefComplaint = backStackEntry.arguments?.getString("chiefComplaint")
            val pastMedicalHistory = backStackEntry.arguments?.getString("pastMedicalHistory")
            val medicalDiagnosis = backStackEntry.arguments?.getString("medicalDiagnosis")
            val medicalPrescription = backStackEntry.arguments?.getString("medicalPrescription")
            val testId = backStackEntry.arguments?.getString("testId")
            EditTestDetailsScreen(
                navController = navController,
                patientIdReceived = patientId,
                fNameReceived = firstName,
                lNameReceived = lastName,
                bloodPressureReceived = bloodPressure,
                respiratoryRateReceived = respiratoryRate,
                heartbeatRateReceived = heartbeatRate,
                bloodOxygenLevelReceived = bloodOxygenLevel,
                chiefComplaintReceived = chiefComplaint,
                pastMedicalHistoryReceived = pastMedicalHistory,
                medicalDiagnosisReceived = medicalDiagnosis,
                medicalPrescriptionReceived = medicalPrescription,
                testIdReceived = testId,
            )
        }
    }
}



// Define your AppTheme using Material 3
@Composable
fun AppTheme(content: @Composable () -> Unit) {
    // Define your custom color scheme
    val colorScheme = lightColorScheme(
        primary = Color(0xFF880808), // Accent color
        secondary = Color(0xFFE0E0E0), // Background color for text fields
        onPrimary = Color.White // Text color on primary background
    )

    // Apply the MaterialTheme using Material 3
    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}
