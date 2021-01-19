package com.rickmune.mobileassignrnd.di

import com.rickmune.mobileassignrnd.domain.SearchCityInteractor
import com.rickmune.mobileassignrnd.domain.SearchCityUseCases
import com.rickmune.mobileassignrnd.viewmodel.SearchCityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    single<SearchCityUseCases> { SearchCityInteractor(get()) }
    viewModel { SearchCityViewModel( get() ) }
}
