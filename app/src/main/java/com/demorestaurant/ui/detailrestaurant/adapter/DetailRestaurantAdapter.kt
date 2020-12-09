package com.demorestaurant.ui.detailrestaurant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demorestaurant.R
import com.demorestaurant.data.remote.DetailRestaurantResponse
import com.demorestaurant.databinding.ItemDetailRestaurantBinding

class DetailRestaurantAdapter :
    ListAdapter<DetailRestaurantResponse, DetailRestaurantAdapter.DetailRestaurantViewHolder>(
        DetailRestaurantDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailRestaurantViewHolder {
        val binding: ItemDetailRestaurantBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_detail_restaurant,
                parent,
                false
            )
        return DetailRestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailRestaurantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DetailRestaurantViewHolder(
        private val binding: ItemDetailRestaurantBinding,
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(item: DetailRestaurantResponse) {
            binding.apply {
                detailRestaurant = item
                executePendingBindings()
            }
        }
    }

}
