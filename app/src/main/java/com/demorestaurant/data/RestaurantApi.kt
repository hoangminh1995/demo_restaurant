package com.demorestaurant.data

import com.demorestaurant.data.remote.RestaurantResponse
import io.reactivex.Single
import retrofit2.http.*

interface RestaurantApi {

    @GET("restaurant")
    fun getListRestaurant() : Single<List<RestaurantResponse>>

}