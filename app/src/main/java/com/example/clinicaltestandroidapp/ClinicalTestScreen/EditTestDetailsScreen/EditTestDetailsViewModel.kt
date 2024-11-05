package com.example.clinicaltestandroidapp.ClinicalTestScreen.EditTestDetailsScreen

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clinicaltestandroidapp.HomeScreen.EditPatientDetailsScreen.EditPatientModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.delay
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class EditTestDetailsViewModel() : ViewModel() {


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _editPatientSuccess = MutableStateFlow(false)
    val editPatientSuccess: StateFlow<Boolean> = _editPatientSuccess

    private val _showToast = MutableStateFlow(false)
    val showToast: StateFlow<Boolean> = _showToast

    private val client = OkHttpClient()

    fun dismissToast() {
        _showToast.value = false
    }

    private fun showSuccessToast() {
        _showToast.value = true
        viewModelScope.launch(Dispatchers.Main) {
            delay(2000)
            dismissToast()
        }
    }

    private fun showFailureToast() {
        _showToast.value = true
        viewModelScope.launch(Dispatchers.Main) {
            delay(2000)
            dismissToast()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun editPatient(context: Context,
                    bloodOxygenLevel: Int,
                    bloodPressure : Int,
                    respiratoryRate : Int,
                    heartbeatRate : Int,
                    chiefComplaint : String,
                    pastMedicalHistory : String,
                    medicalDiagnosis : String,
                    medicalPrescription : String,
                    patientId : String,
                    testId : String
    ) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .withZone(ZoneOffset.UTC)
        val currentDateTime = formatter.format(Instant.now())
        _isLoading.value = true
        val editData = mapOf(
            "bloodPressure" to bloodPressure,
            "respiratoryRate" to respiratoryRate,
            "heartbeatRate" to heartbeatRate,
            "bloodOxygenLevel" to bloodOxygenLevel,
            "chiefComplaint" to chiefComplaint.toString(),
            "pastMedicalHistory" to pastMedicalHistory.toString(),
            "medicalDiagnosis" to medicalDiagnosis.toString(),
            "medicalPrescription" to medicalPrescription,
            "patientId" to patientId,
            "creationDateTime" to currentDateTime
        )

        val json = Gson().toJson(editData)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url("https://group3-mapd713.onrender.com/api/clinical-tests/clinical-tests/$testId")
            .put(requestBody)
            .build()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    val loginResponse = Gson().fromJson(responseBody, EditPatientModel::class.java)
                    if (loginResponse.success == true) {
                        _editPatientSuccess.value = true
//                        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                        showSuccessToast()
                    } else {
                        showFailureToast()
                    }
                } else {
                    showFailureToast()
                }
            } catch (e: Exception) {
                showFailureToast()
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}