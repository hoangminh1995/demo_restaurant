package com.demorestaurant.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.demorestaurant.R
import com.demorestaurant.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this
                , R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
    }
}