package com.example.clinicaltestandroidapp.RegistrationScreen

import com.example.clinicaltestandroidapp.RegistrationScreen.RegistrationModel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

class RegistrationViewModel() : ViewModel() {


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _registerSuccess = MutableStateFlow(false)
    val registerSuccess: StateFlow<Boolean> = _registerSuccess

    private val _showToast = MutableStateFlow(false)
    val showToast: StateFlow<Boolean> = _showToast

    private val client = OkHttpClient()

//    init {
//        checkIfLoggedIn()
//    }

//    private fun checkIfLoggedIn() {
//        _loginSuccess.value = sharedPreferences.getBoolean("isLoggedIn", false)
//    }
//
//    fun logOut() {
//        sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
//        _loginSuccess.value = false
//    }

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

    fun register(context: Context,
              email: String,
              password : String,
              firstName : String,
              lastName : String,
              phoneNumber : String,
              address : String,
              gender : String,
              healthcareProvider : String,
    ) {
        _isLoading.value = true
        val loginData = mapOf(
            "firstName" to firstName.toString(),
            "lastName" to lastName.toString(),
            "phoneNumber" to phoneNumber.toString(),
            "email" to email.toString(),
            "password" to password.toString(),
            "address" to address.toString(),
            "gender" to if (gender == "Male") "0" else "1",
            "healthcareProvider" to if (healthcareProvider == "Doctor") "0" else "1",
        )

        val json = Gson().toJson(loginData)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url("https://group3-mapd713.onrender.com/auth/register")
            .post(requestBody)
            .build()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    val loginResponse = Gson().fromJson(responseBody, RegistrationModel::class.java)
                    if (loginResponse.success == true) {
                        _registerSuccess.value = true
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
