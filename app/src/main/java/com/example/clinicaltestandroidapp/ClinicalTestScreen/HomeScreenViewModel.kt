package com.example.clinicaltestandroidapp.ClinicalTestScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ClinicalTestViewModel : ViewModel() {

    // Mutable state for loading indicator and patients list
    var isLoading = mutableStateOf(false)
        private set

    var patients = mutableStateOf<List<PatientRecord>>(emptyList())
        private set

    // Coroutine exception handler
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Failed to fetch patient data: ${exception.localizedMessage}")
        isLoading.value = false
    }
    fun clearPatientData() {
        patients.value = emptyList() // Clear the data
    }

    // Function to fetch patient data
    fun fetchPatientData() {
        isLoading.value = true

        // Using coroutine to perform network request
        viewModelScope.launch(exceptionHandler) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://group3-mapd713.onrender.com/api/clinical-tests/clinical-tests")
                .get()
                .build()

            try {
                val response = withContext(Dispatchers.IO) {
                    client.newCall(request).execute()
                }

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        val type = object : TypeToken<ClinicalTestModel>() {}.type
                        val patientResponse: ClinicalTestModel = Gson().fromJson(responseBody, type)

                        // Update the patients list
                        patients.value = patientResponse.data ?: emptyList()
                        println("Successfully fetched patient data")
                    } else {
                        println("No patients found")
                    }
                } else {
                    println("Failed to fetch patient data: ${response.message}")
                }
            } catch (e: Exception) {
                println("Failed to fetch patient data: ${e.localizedMessage}")
            } finally {
                isLoading.value = false
            }
        }
    }
}
