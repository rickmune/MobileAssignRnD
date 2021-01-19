package com.rickmune.mobileassignrnd.domain

import com.squareup.moshi.JsonClass
import kotlinx.coroutines.flow.Flow
import java.io.Serializable

interface SearchCityUseCases {

    suspend fun loadCity(fileName: String): Flow<List<City>?>

    suspend fun searchCity(cities: List<City>, param: String): Flow<List<City>>
}

@JsonClass(generateAdapter = true)
data class City(val country: String, val name: String, val coord: Coord): Serializable {
    constructor() : this("", "", Coord(0.0, 0.0))
}

@JsonClass(generateAdapter = true)
data class Coord(val lon: Double, val lat: Double): Serializable