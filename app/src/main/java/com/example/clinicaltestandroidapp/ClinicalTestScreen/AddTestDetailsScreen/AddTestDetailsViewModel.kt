package com.example.clinicaltestandroidapp.ClinicalTestScreen.AddTestDetailsScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clinicaltestandroidapp.HomeScreen.AddPatientDetailsScreen.AddTestModel
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

class AddTestDetailsViewModel() : ViewModel() {


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _addPatientSuccess = MutableStateFlow(false)
    val addPatientSuccess: StateFlow<Boolean> = _addPatientSuccess

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

    fun addPatient(context: Context,
                 email: String,
                 firstName : String,
                 lastName : String,
                 phoneNumber : String,
                 address : String,
                 gender : String,
                   height : String,
                   weight : String
    ) {
        _isLoading.value = true
        val loginData = mapOf(
            "firstName" to firstName.toString(),
            "lastName" to lastName.toString(),
            "phoneNumber" to phoneNumber.toString(),
            "email" to email.toString(),
            "address" to address.toString(),
            "gender" to if (gender == "Male") "0" else "1",
            "status" to "normal",
            "height" to height,
            "weight" to weight,
        )

        val json = Gson().toJson(loginData)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url("https://group3-mapd713.onrender.com/patient/add")
            .post(requestBody)
            .build()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    val loginResponse = Gson().fromJson(responseBody, AddTestModel::class.java)
                    if (loginResponse.success == true) {
                        _addPatientSuccess.value = true
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