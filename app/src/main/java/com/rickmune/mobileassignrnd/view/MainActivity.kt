package com.rickmune.mobileassignrnd.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.rickmune.mobileassignrnd.R
import com.rickmune.mobileassignrnd.databinding.ActivityMainBinding
import com.rickmune.mobileassignrnd.viewmodel.CityFoundState
import com.rickmune.mobileassignrnd.viewmodel.DefaultState
import com.rickmune.mobileassignrnd.viewmodel.LoadingState
import com.rickmune.mobileassignrnd.viewmodel.SearchCityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
