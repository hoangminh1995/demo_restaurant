package com.demorestaurant.data.remote

import com.demorestaurant.data.type.DayWorking
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DetailRestaurantResponse(
    @SerializedName("day")
    @Expose
    val day: DayWorking,
    @SerializedName("time_work")
    @Expose
    val timeWork: String?
)