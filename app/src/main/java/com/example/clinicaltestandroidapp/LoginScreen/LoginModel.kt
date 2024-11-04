package com.example.clinicaltestandroidapp.LoginScreen


// Main LoginModel data class
data class LoginModel(
    val success: Boolean,
    val token: String?,
    val user: User?
) {
    // Nested User data class
    data class User(
        val id: String,
        val firstName: String,
        val lastName: String,
        val email: String,
        val phoneNumber: String,
        val gender: Int,
        val healthcareProvider: Int
    )
}
