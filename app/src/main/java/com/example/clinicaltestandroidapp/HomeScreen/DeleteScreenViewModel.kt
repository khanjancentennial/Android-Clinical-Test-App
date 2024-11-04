package com.example.clinicaltestandroidapp.HomeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class DeletePatientViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _patientDeleteSuccess = MutableStateFlow(false)
    val patientDeleteSuccess: StateFlow<Boolean> = _patientDeleteSuccess

    private val _showToast = MutableStateFlow(false)
    val showToast: StateFlow<Boolean> = _showToast

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage

    fun deletePatient(patientId: String, completion: (Boolean) -> Unit) {
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val urlString = "https://group3-mapd713.onrender.com/patient/delete/$patientId"
            var connection: HttpURLConnection? = null
            var success = false

            try {
                val url = URL(urlString)
                connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "DELETE"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode in 200..299) {
                    val inputStream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val response = reader.readText()
                    reader.close()

                    val jsonResponse = JSONObject(response)
                    val message = jsonResponse.optString("message", "Patient deleted successfully")

                    withContext(Dispatchers.Main) {
                        _patientDeleteSuccess.value = true
                        _toastMessage.value = message
                        Log.d("DeletePatient", "Delete Patient response: $message")
                        success = true
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        _toastMessage.value = "Failed to delete patient."
                        Log.e("DeletePatient", "Patient delete failed with response code: $responseCode")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _toastMessage.value = "Failed to delete patient: ${e.message}"
                    Log.e("DeletePatient", "Patient delete failed: ${e.message}")
                }
            } finally {
                connection?.disconnect()
                withContext(Dispatchers.Main) {
                    _isLoading.value = false
                    _showToast.value = true
                    completion(success)
                }
            }
        }
    }
}
