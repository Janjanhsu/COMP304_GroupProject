package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.Current
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.Location


@Entity(tableName = "weather")
@TypeConverters(Converters::class)
data class WeatherEntity(
    var location: Location,
    @PrimaryKey
    val name: String = location.name,
    var current: Current,
    @ColumnInfo(defaultValue = "0")
    var isFavorite: Boolean
)
