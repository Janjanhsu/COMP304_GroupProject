package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model

import kotlinx.serialization.Serializable

@Serializable
data class Current(
    val temp_c: Double,
    val condition: Condition,
    val humidity: Int,
    val cloud: Int,
    val feelslike_c: Double
)