package com.example.yichen.yichen_kwokwing_comp304sec001_lab04.data

import java.io.File
import java.io.InputStream

data class Location(val name: String, val category: String, val address: String, val photo: String)

fun readCsv(fileName: String): List<Location> {
    val locations = mutableListOf<Location>()
    File(fileName).forEachLine { line ->
        val parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)".toRegex()).map { it.trim('"') }
        if (parts.size == 4) {
            locations.add(Location(parts[0], parts[1], parts[2], parts[3]))
        }

    }
    return locations
}
fun readCsv(inputStream: InputStream): Locations {
    val locations = Locations()
    inputStream.bufferedReader().forEachLine { line ->
        val parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)".toRegex()).map { it.trim('"') }
        if (parts.size == 4) {
            locations.add(Location(parts[0], parts[1], parts[2], parts[3]))
        }
    }
    return locations
}