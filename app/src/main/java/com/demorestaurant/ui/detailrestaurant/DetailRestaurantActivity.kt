package com.demorestaurant.ui.detailrestaurant

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.demorestaurant.R
import com.demorestaurant.data.remote.RestaurantResponse
import com.demorestaurant.databinding.ActivityDetailRestaurantBinding
import com.demorestaurant.di.module.ViewModelProviderFactory
import com.demorestaurant.ui.listrestaurant.adapter.RestaurantAdapter
import com.demorestaurant.utils.AppConstant
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class DetailRestaurantActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var mViewModel: DetailRestaurantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDetailRestaurantBinding>(
            this
            , R.layout.activity_detail_restaurant
        )
        val restaurantIntent:RestaurantResponse = intent.getParcelableExtra<RestaurantResponse>(AppConstant.RESTAURANT) as RestaurantResponse

        binding.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            restaurant = restaurantIntent
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}