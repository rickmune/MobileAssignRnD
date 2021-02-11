package com.rickmune.mobileassignrnd

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.rickmune.mobileassignrnd.domain.City
import com.rickmune.mobileassignrnd.domain.Coord
import com.rickmune.mobileassignrnd.domain.SearchCityInteractor
import com.rickmune.mobileassignrnd.domain.SearchCityUseCases
import com.rickmune.mobileassignrnd.viewmodel.LoadingState
import com.rickmune.mobileassignrnd.viewmodel.SearchCityState
import com.rickmune.mobileassignrnd.viewmodel.SearchCityViewModel
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentCaptor.forClass
import org.mockito.Mock
import org.mockito.Mockito.*
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

    var context: Context = mock(Context::class.java)

    @Test
    fun giveBackList_whenSearchStringExists() {
        testCoroutineRule.runBlockingTest {

            val argument: ArgumentCaptor<SearchCityState> = forClass(SearchCityState::class.java)
            val viewModel = SearchCityViewModel(searchUseCase)
            viewModel.stateLiveData.observeForever(searchResultObserver)
            viewModel.searchCity("A")
            verify(searchResultObserver, times(2)).onChanged(argument.capture())
            assertTrue(argument.allValues[0] == LoadingState(true, emptyList()))
            assertTrue(argument.allValues[1] == LoadingState(true, emptyList()))

            viewModel.stateLiveData.removeObserver(searchResultObserver)
        }
    }

    @Test
    fun test_searchUseCase_city_found() {
        testCoroutineRule.runBlockingTest {
            val useCase = SearchCityInteractor(context)
            var cities = mutableListOf<City>()
            cities.add(City("KE", "Nairobi", Coord(0.00, 0.00)))
            cities.add(City("KE", "Kisumu", Coord(0.00, 0.00)))
            val result = useCase.searchCity(cities, "Nairobi").take(1).first()
            assertTrue(result.isNotEmpty())
            assertTrue(result.size == 1)
            assertTrue(result[0].name == "Nairobi")
        }
    }

    @Test
    fun test_searchUseCase_city_not_found() {
        testCoroutineRule.runBlockingTest {
            val useCase = SearchCityInteractor(context)
            var cities = mutableListOf<City>()
            cities.add(City("KE", "Nairobi", Coord(0.00, 0.00)))
            cities.add(City("KE", "Kisumu", Coord(0.00, 0.00)))
            val result = useCase.searchCity(cities, "Mombasa").take(1).first()
            assertTrue(result.isEmpty())
        }
    }
}