package com.demorestaurant.ui.listrestaurant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demorestaurant.R
import com.demorestaurant.data.remote.RestaurantResponse
import com.demorestaurant.databinding.ItemRestaurantBinding

class RestaurantAdapter(
    private val callback: CallbackListRestaurant
) : ListAdapter<RestaurantResponse, RestaurantAdapter.RestaurantViewHolder>(RestaurantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding: ItemRestaurantBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_restaurant,
                parent,
                false
            )
        return RestaurantViewHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RestaurantViewHolder(
        private val binding: ItemRestaurantBinding,
        private val callback: CallbackListRestaurant
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                binding.restaurant?.let {
                    callback.onItemClick(it)
                }
            }
        }

        fun bind(item: RestaurantResponse) {
            binding.apply {
                restaurant = item
                executePendingBindings()
            }
        }
    }

    interface CallbackListRestaurant {
        fun onItemClick(item: RestaurantResponse)
    }
}
