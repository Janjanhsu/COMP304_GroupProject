package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [WeatherEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    // Create an actual database
    companion object {
        // Annotation for letting other threads immediately know the changes, we will only have one single instance of the database
        @Volatile
        private var instance: WeatherDatabase? = null
        private val LOCK = Any()

        // everytime recreate it, it will be called
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WeatherDatabase::class.java,
                "weather_database"
            ).build()
    }
}
