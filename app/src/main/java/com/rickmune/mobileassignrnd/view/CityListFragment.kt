package com.rickmune.mobileassignrnd.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rickmune.mobileassignrnd.R
import com.rickmune.mobileassignrnd.databinding.FragmentCityListBinding
import com.rickmune.mobileassignrnd.domain.City
import com.rickmune.mobileassignrnd.extensions.observeEvent
import com.rickmune.mobileassignrnd.extensions.viewBinding
import com.rickmune.mobileassignrnd.viewmodel.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class CityListFragment: Fragment(R.layout.fragment_city_list) {

    private val binding by viewBinding(FragmentCityListBinding::bind)
    private val viewModel: SearchCityViewModel by viewModel()

    private val cityListAdapter by lazy {
        CityListAdapter { onCityPicked(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setUpObservers()
        binding.citiesList.adapter = cityListAdapter
        binding.search.editText?.doAfterTextChanged {
            it?.let {
                viewModel.searchCity(it.toString())
            }
        }
    }

    private fun setUpObservers() {
        viewModel.interactions.observeEvent(viewLifecycleOwner) {
            when(it) {
                is Navigate -> findNavController().navigate(it.destination)
            }
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner) {
            when(it){
                is DefaultState -> {
                    if (it.data.isNotEmpty()) cityListAdapter.setCities(it.data)
                }
                is LoadingState -> {
                    Log.d("MainActivity", "onCreate LoadingState: " + it.data.size)
                }
                is CityFoundState -> {
                    cityListAdapter.setCities(it.data)
                }
            }
        }
    }

    private fun setupToolbar() {
        binding.apply {
            toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
            toolbar.setOnMenuItemClickListener {
                onOptionsItemSelected(it)
            }

        }
    }

    private fun onCityPicked(city: City) {
        viewModel.citySelected(city)
    }

}
