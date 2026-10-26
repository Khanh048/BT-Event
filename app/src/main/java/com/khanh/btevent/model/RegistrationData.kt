package com.khanh.btevent.model

import java.io.Serializable

data class RegistrationData(
    val fullName: String,
    val email: String,
    val phone: String,
    val studentId: String,
    val gender: String,
    val socialNetworks: List<String>
) : Serializable
