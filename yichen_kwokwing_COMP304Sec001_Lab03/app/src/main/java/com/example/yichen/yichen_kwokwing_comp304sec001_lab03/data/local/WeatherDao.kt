package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather w " +
            "INNER JOIN locations l " +
            "ON w.locationId = l.id INNER JOIN currents c ON w.currentId = c.id " +
            "where l.name = :location")
    suspend fun getWeatherForLocation(location: String): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Query("SELECT * FROM weather")
    fun getAllWeather(): LiveData<List<WeatherEntity>>

    @Delete
    suspend fun deleteWeather(weather: WeatherEntity)
}