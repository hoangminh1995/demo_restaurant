package com.demorestaurant.ui.detailrestaurant

import androidx.lifecycle.ViewModel
import com.demorestaurant.repository.RestaurantRepository
import javax.inject.Inject

class DetailRestaurantViewModel @Inject constructor(
        private val restaurantRepository: RestaurantRepository) : ViewModel() {


}