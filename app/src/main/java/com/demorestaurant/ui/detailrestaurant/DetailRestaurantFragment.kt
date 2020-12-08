package com.demorestaurant.ui.detailrestaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.demorestaurant.R
import com.demorestaurant.databinding.FragmentDetailRestaurantBinding
import com.demorestaurant.di.module.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DetailRestaurantFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var mViewModel: DetailRestaurantViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentDetailRestaurantBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this, factory).get(DetailRestaurantViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }
}