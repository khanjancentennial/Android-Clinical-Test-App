package com.example.clinicaltestandroidapp.HomeScreen

// Root model for the API response
data class HomeScreenModel(
    val success: Boolean,
    val data: List<PatientData>?
)

// Model for individual patient data
data class PatientData(
    val _id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val weight: String,
    val height: String,
    val address: String,
    val gender: Int,
    val status: String
) {
    // Property to provide an ID derived from _id
    val id: String
        get() = _id
}
