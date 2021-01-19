package com.rickmune.mobileassignrnd.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickmune.mobileassignrnd.domain.City
import com.rickmune.mobileassignrnd.domain.SearchCityUseCases
import com.rickmune.mobileassignrnd.extensions.Event
import com.rickmune.mobileassignrnd.extensions.asEvent
import com.rickmune.mobileassignrnd.view.CityListFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchCityViewModel(private val useCases: SearchCityUseCases): ViewModel() {

    private val _stateLiveData =  MutableLiveData<SearchCityState>()
    val stateLiveData: LiveData<SearchCityState> = _stateLiveData

    private val _interactions =  MutableLiveData<Event<SearchCityActions>>()
    val interactions: LiveData<Event<SearchCityActions>> = _interactions

    lateinit var allCities: List<City>

    val fileName = "cities.json"

    init {
        loadCities()
    }

    private fun loadCities() {
        _stateLiveData.postValue(LoadingState(true, emptyList()))
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val cities = useCases.loadCity(fileName)
                cities.collect { value ->
                    if (value != null) {
                        allCities = value.sortedBy { it.name }
                        _stateLiveData.postValue( DefaultState(false, allCities))
                    }
                }
            }
        }
    }

    fun searchCity(search: String) {
        if (search.isEmpty()){
            _stateLiveData.postValue(DefaultState(false, allCities))
        } else {
            _stateLiveData.postValue(LoadingState(true, emptyList()))
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val cities = useCases.searchCity(allCities, search)
                    cities.collect { value ->
                        Log.d("SearchCityViewModel", "searchCity: ${value.size}")
                        _stateLiveData.postValue(CityFoundState(false, value))
                    }
                }
            }
        }
    }

    fun citySelected(city: City) {
        _interactions.postValue(Navigate(CityListFragmentDirections.toMapFragment(city)).asEvent())
    }

}