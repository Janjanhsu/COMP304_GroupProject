package com.example.yichen.yichen_kwokwing_comp304sec001_lab04.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Route(
    @SerializedName("overview_polyline")
    val overviewPolyline: OverviewPolyline
)