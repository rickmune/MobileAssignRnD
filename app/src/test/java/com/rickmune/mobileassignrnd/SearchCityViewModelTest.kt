package com.rickmune.mobileassignrnd

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.rickmune.mobileassignrnd.domain.SearchCityUseCases
import com.rickmune.mobileassignrnd.viewmodel.LoadingState
import com.rickmune.mobileassignrnd.viewmodel.SearchCityState
import com.rickmune.mobileassignrnd.viewmodel.SearchCityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchCityViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var searchUseCase: SearchCityUseCases

    @Mock
    private lateinit var searchResultObserver: Observer<SearchCityState>

    @Test
    fun giveBackList_whenSearchStringExists() {
        testCoroutineRule.runBlockingTest {

            val viewModel = SearchCityViewModel(searchUseCase)
            viewModel.stateLiveData.observeForever(searchResultObserver)
            viewModel.searchCity("A")
            verify(searchResultObserver, times(2)).onChanged(LoadingState(true, emptyList()))
            viewModel.stateLiveData.removeObserver(searchResultObserver)
        }
    }
}