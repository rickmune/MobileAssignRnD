package com.rickmune.mobileassignrnd.viewmodel

import androidx.navigation.NavDirections

sealed class SearchCityActions {
    abstract val destination: NavDirections
}

data class Navigate(override val destination: NavDirections) : SearchCityActions()
