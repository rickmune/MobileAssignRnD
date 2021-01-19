package com.rickmune.mobileassignrnd.view

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rickmune.mobileassignrnd.R
import com.rickmune.mobileassignrnd.databinding.FragmentMapsBinding
import com.rickmune.mobileassignrnd.extensions.viewBinding

class MapsFragment : Fragment(R.layout.fragment_maps) {

    private val args: MapsFragmentArgs by navArgs()
    private val binding by viewBinding(FragmentMapsBinding::bind)

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        args.city.coord.lat
        val location = LatLng(args.city.coord.lat, args.city.coord.lon)
        googleMap.addMarker(MarkerOptions().position(location).title(args.city.name))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        setupToolbar()
    }

    private fun setupToolbar() {
        binding.apply {
            toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
            toolbarTitle.text = "${args.city.name} ${args.city.country}"
        }
    }
}