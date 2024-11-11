package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.TypeConverters
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.Current
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.Location
import java.time.LocalDate

@Entity(tableName = "weather")
@TypeConverters(Converters::class)
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val location: Location,
    val current: Current,
    @ColumnInfo(defaultValue = "0")
    val isFavorite: Boolean
)
