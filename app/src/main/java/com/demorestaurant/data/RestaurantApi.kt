package com.demorestaurant.data

import com.demorestaurant.data.remote.DetailRestaurantResponse
import com.demorestaurant.data.remote.RestaurantResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantApi {

    @GET("restaurant")
    fun getListRestaurant(): Single<List<RestaurantResponse>>

    @GET("detail_restaurant")
    fun getDetailRestaurant(@Query("restaurant_id") restaurantId: Long): Single<List<DetailRestaurantResponse>>

}