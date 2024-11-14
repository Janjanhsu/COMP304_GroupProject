package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model

import kotlinx.serialization.Serializable

@Serializable
data class Current(
    var temp_c: Double,
    val condition: Condition,
    var humidity: Int,
    var cloud: Int,
    var feelslike_c: Double
)