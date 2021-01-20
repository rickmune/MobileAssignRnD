package com.rickmune.mobileassignrnd.domain

import android.content.Context
import com.rickmune.mobileassignrnd.utils.loadCities
import com.rickmune.mobileassignrnd.utils.searchBinaryPlus
import kotlinx.coroutines.flow.Flow

class SearchCityInteractor(private val cxt: Context): SearchCityUseCases {

    override suspend fun loadCity(fileName: String): Flow<List<City>?> {
        return loadCities(cxt, fileName)
    }

    override suspend fun searchCity(cities: List<City>, param: String): Flow<List<City>> {
        return searchBinaryPlus(cities, param)
    }
}