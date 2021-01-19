package com.rickmune.mobileassignrnd.viewmodel

import com.rickmune.mobileassignrnd.domain.City

sealed class SearchCityState {
    abstract val isLoading: Boolean
}

data class DefaultState(override val isLoading: Boolean, val data: List<City>) : SearchCityState()
data class CityFoundState(override val isLoading: Boolean, val data: List<City>) : SearchCityState()
data class LoadingState(override val isLoading: Boolean, val data: List<City>) : SearchCityState()
data class ErrorState(val errorMessage: String, override val isLoading: Boolean) : SearchCityState()
