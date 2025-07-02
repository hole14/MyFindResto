package com.example.myfindresto.viewModel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myfindresto.model.RestoModel
import com.example.myfindresto.repository.RestoRepository
import kotlinx.coroutines.launch

data class RestoWithDistance(
    val restaurant: RestoModel,
    val distance: Double
)

class RestoViewModel(private val repository: RestoRepository): ViewModel() {
    private val _retaurants = MutableLiveData<List<RestoWithDistance>>()
    val restaurants: LiveData<List<RestoWithDistance>> = _retaurants

    fun loadRestaurants(userLat: Double, userLng: Double) {
        viewModelScope.launch {
            val restoList = repository.fetchRestaurants()
            val withDistance = restoList.map {
                val dist = calculateDistance(userLat, userLng, it.latitude, it.longtitude)
                RestoWithDistance(it, dist)
            }.sortedBy { it.distance }
            _retaurants.value = withDistance
        }
    }
    fun calculateDistance (
        userLat: Double, userLng: Double,
        restoLat: Double, restoLng: Double
    ): Double {
        val result = FloatArray(1)
        Location.distanceBetween(userLat, userLng, restoLat, restoLng, result)
        return result[0] / 1000.0
    }
}

class RestaurantViewModelFactory(private val repository: RestoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RestoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}