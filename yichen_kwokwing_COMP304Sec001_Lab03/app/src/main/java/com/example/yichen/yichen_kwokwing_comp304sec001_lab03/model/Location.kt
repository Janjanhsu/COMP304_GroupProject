package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val tz_id: String,
    var localtime_epoch: Int,
    var localtime: String
)