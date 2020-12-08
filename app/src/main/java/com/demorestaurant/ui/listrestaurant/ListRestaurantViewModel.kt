package com.demorestaurant.ui.listrestaurant

import androidx.lifecycle.ViewModel
import com.demorestaurant.repository.RestaurantRepository
import javax.inject.Inject

class ListRestaurantViewModel @Inject constructor(
        private val restaurantRepository: RestaurantRepository) : ViewModel() {


}