package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currents")
data class Current(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    val temp_c: Double
)