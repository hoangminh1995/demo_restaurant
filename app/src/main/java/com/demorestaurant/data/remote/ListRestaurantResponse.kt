package com.demorestaurant.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListRestaurantResponse(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("name")
    @Expose
    val name: String
)