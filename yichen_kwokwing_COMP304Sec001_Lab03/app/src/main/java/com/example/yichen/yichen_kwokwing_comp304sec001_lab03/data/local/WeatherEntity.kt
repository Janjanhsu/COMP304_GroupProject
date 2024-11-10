package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.TypeConverters
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.Current
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.Location

@Entity(
    tableName = "weather",
    foreignKeys = [
        ForeignKey(
            entity = Location::class,
            parentColumns = ["id"],
            childColumns = ["locationId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Current::class,
            parentColumns = ["id"],
            childColumns = ["currentId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@TypeConverters(Converters::class)
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val locationId: Int,
    val currentId: Int,
    @ColumnInfo(defaultValue = "0")
    val isFavorite: Boolean
)
