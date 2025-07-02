package com.example.myfindresto

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfindresto.adapter.RestoAdapter
import com.example.myfindresto.databinding.ActivityMainBinding
import com.example.myfindresto.repository.RestoRepository
import com.example.myfindresto.viewModel.RestaurantViewModelFactory
import com.example.myfindresto.viewModel.RestoViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var adapter: RestoAdapter

    private val viewModel: RestoViewModel by viewModels {
        RestaurantViewModelFactory(RestoRepository())
    }
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = RestoAdapter(listOf())
        binding.rvResto.layoutManager = LinearLayoutManager(this)
        binding.rvResto.adapter = adapter

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getUserLocationAndLoadRestaurants()

        viewModel.restaurants.observe(this) { list ->
            adapter.updateData(list)
        }
    }
    private fun getUserLocationAndLoadRestaurants() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    val lat = location?.latitude ?: -6.2
                    val lng = location?.longitude ?: 106.8
                    viewModel.loadRestaurants(lat, lng)
                }
        }
    }
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                getUserLocationAndLoadRestaurants()
            }
        }
}