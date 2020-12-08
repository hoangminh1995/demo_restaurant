package com.demorestaurant.ui.listrestaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.demorestaurant.R
import com.demorestaurant.databinding.FragmentListRestaurantBinding
import com.demorestaurant.di.module.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ListRestaurantFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var mViewModel: ListRestaurantViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentListRestaurantBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this, factory).get(ListRestaurantViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
}