package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.local

import androidx.room.TypeConverter
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.Condition
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.Current
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.Location
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class Converters {
    private val json = Json { ignoreUnknownKeys = true }
    @TypeConverter
    fun fromLocation(location: Location): String = Json.encodeToString(location)

    @TypeConverter
    fun toLocation(locationString: String): Location = Json.decodeFromString(locationString)

    @TypeConverter
    fun fromCurrent(current: Current): String = Json.encodeToString(current)

    @TypeConverter
    fun toCurrent(currentString: String): Current = Json.decodeFromString(currentString)

    @TypeConverter
    fun fromCondition(condition: Condition): String {
        return condition.icon
    }

    @TypeConverter
    fun toCondition(value: String): Condition {
        return Condition(icon = value)
    }
}