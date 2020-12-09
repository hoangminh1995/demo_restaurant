package com.demorestaurant.ui.detailrestaurant.adapter

import androidx.recyclerview.widget.DiffUtil
import com.demorestaurant.data.remote.DetailRestaurantResponse
import com.demorestaurant.data.remote.RestaurantResponse

class DetailRestaurantDiffCallback : DiffUtil.ItemCallback<DetailRestaurantResponse>() {

    override fun areItemsTheSame(oldItem: DetailRestaurantResponse, newItem: DetailRestaurantResponse): Boolean {
        return oldItem.day == newItem.day
    }

    override fun areContentsTheSame(oldItem: DetailRestaurantResponse, newItem: DetailRestaurantResponse): Boolean {
        return oldItem.timeWork == newItem.timeWork
    }
}