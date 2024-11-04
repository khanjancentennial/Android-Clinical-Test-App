package com.example.clinicaltestandroidapp.LoginScreen

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
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel() : ViewModel() {


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess.asStateFlow()

    private val _showToast = MutableStateFlow(false)
    val showToast: StateFlow<Boolean> = _showToast

    private val client = OkHttpClient()

//    init {
//        checkIfLoggedIn()
//    }
//
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
//            dismissToast()
            _loginSuccess.value = true
        }
    }

    private fun showFailureToast() {
        _showToast.value = true
        viewModelScope.launch(Dispatchers.Main) {
            delay(2000)
            dismissToast()
        }
    }

    fun login(context: Context, email: String, password : String) {

        _isLoading.value = true
        print("loading : $isLoading")
        val loginData = mapOf(
            "email" to email,
            "password" to password
        )

        val json = Gson().toJson(loginData)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url("https://group3-mapd713.onrender.com/auth/login")
            .post(requestBody)
            .build()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    val loginResponse = Gson().fromJson(responseBody, LoginModel::class.java)
                    if (loginResponse.success) {
                        _loginSuccess.value = true
                        println("Success Message: ${loginSuccess.value}")
//                        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                        showSuccessToast()

                    } else {
                        showFailureToast()
                        _isLoading.value = false
                        _loginSuccess.value = false
                    }
                } else {
                    showFailureToast()
                    _isLoading.value = false
                    _loginSuccess.value = false
                }
            } catch (e: Exception) {
                showFailureToast()
                e.printStackTrace()
                _isLoading.value = false
                _loginSuccess.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}
