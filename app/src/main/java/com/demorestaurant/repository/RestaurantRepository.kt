package com.demorestaurant.repository

import com.demorestaurant.data.RestaurantApi
import com.demorestaurant.data.remote.DetailRestaurantResponse
import com.demorestaurant.data.remote.RestaurantResponse
import io.reactivex.Single

class RestaurantRepository(private val apiHelper: RestaurantApi) {

    fun getListRestaurantRemote(): Single<List<RestaurantResponse>> {
        return apiHelper.getListRestaurant()
    }

    fun getDetailRestaurantRemote(restaurantId: Long): Single<List<DetailRestaurantResponse>> {
        return apiHelper.getDetailRestaurant(restaurantId)
    }

}