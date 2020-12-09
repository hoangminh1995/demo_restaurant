package com.demorestaurant.repository

import com.demorestaurant.data.RestaurantApi
import com.demorestaurant.data.remote.RestaurantResponse
import io.reactivex.Single

class RestaurantRepository(val apiHelper: RestaurantApi) {

    fun getListRestaurantRemote() : Single<List<RestaurantResponse>> {
        return apiHelper.getListRestaurant()
    }

}