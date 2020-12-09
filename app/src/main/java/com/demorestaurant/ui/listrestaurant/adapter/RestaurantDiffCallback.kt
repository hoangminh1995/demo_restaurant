package com.demorestaurant.ui.listrestaurant.adapter

import androidx.recyclerview.widget.DiffUtil
import com.demorestaurant.data.remote.RestaurantResponse

class RestaurantDiffCallback : DiffUtil.ItemCallback<RestaurantResponse>() {

    override fun areItemsTheSame(oldItem: RestaurantResponse, newItem: RestaurantResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RestaurantResponse, newItem: RestaurantResponse): Boolean {
        return oldItem.name == newItem.name
    }
}