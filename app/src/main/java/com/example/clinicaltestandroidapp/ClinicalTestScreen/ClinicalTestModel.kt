package com.example.clinicaltestandroidapp.ClinicalTestScreen

// Model class to represent the entire API response
data class ClinicalTestModel(
    val success: Boolean,
    val data: List<PatientRecord> // List of PatientRecord to match the "data" array in the response
)

// Model class to represent each patient record
data class PatientRecord(
    val patient: Patient,
    val _id: String, // Match "_id" as the unique identifier for the record
    val bloodPressure: Int,
    val respiratoryRate: Int,
    val bloodOxygenLevel: Int,
    val heartbeatRate: Int,
    val chiefComplaint: String,
    val pastMedicalHistory: String,
    val medicalDiagnosis: String,
    val medicalPrescription: String,
    val creationDateTime: String,
    val status: String,
    val __v: Int // Match "__v" for the version field
)

// Model class to represent the patient details
data class Patient(
    val _id: String, // Match "_id" as the unique identifier for the patient
    val firstName: String,
    val lastName: String
)


