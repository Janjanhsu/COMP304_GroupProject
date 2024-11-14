package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather w where name = :location")
    suspend fun getWeatherForLocation(location: String): WeatherEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Query("SELECT * FROM weather")
    fun getAllWeather(): Flow<List<WeatherEntity>>

    @Query("SELECT * FROM weather where isFavorite = 1")
    fun getFavoriteLocation(): Flow<List<WeatherEntity>>

    @Delete
    suspend fun deleteWeather(weather: WeatherEntity)

    @Query("UPDATE weather SET isFavorite = 1 WHERE name = :id")
    suspend fun addFavoriteLocation(id: String)
}