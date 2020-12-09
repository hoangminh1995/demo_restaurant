package com.demorestaurant.ui.listrestaurant

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.demorestaurant.R
import com.demorestaurant.data.remote.RestaurantResponse
import com.demorestaurant.databinding.ActivityListRestaurantBinding
import com.demorestaurant.di.module.ViewModelProviderFactory
import com.demorestaurant.ui.base.Command
import com.demorestaurant.ui.detailrestaurant.DetailRestaurantActivity
import com.demorestaurant.ui.listrestaurant.adapter.RestaurantAdapter
import com.demorestaurant.utils.AppConstant
import com.demorestaurant.utils.extension.launchActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_list_restaurant.*
import javax.inject.Inject

class ListRestaurantActivity : DaggerAppCompatActivity(), RestaurantAdapter.CallbackListRestaurant,
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var mViewModel: ListRestaurantViewModel

    private lateinit var mListRestaurantAdapter: RestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityListRestaurantBinding>(
            this
            , R.layout.activity_list_restaurant
        )
        mViewModel = ViewModelProvider(this, factory).get(ListRestaurantViewModel::class.java)

        binding.apply {
            mListRestaurantAdapter = RestaurantAdapter(this@ListRestaurantActivity)
            rcvListRestaurant.adapter = mListRestaurantAdapter
            setSupportActionBar(binding.toolbar)
            swipeLayout.setOnRefreshListener(this@ListRestaurantActivity)
        }

        mViewModel.getListRestaurant()
        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        mViewModel.mListRestaurant.observe(this, Observer { listRestaurantResponse ->
            mListRestaurantAdapter.submitList(listRestaurantResponse)
        })
        mViewModel.mCommand.observe(this, Observer {
            handleCommand(it)
        })
    }

    private fun handleCommand(command: Command) {
        when (command) {
            is Command.OfflineDialog -> Toast.makeText(this, getString(R.string.please_check_internet), Toast.LENGTH_LONG).show()
            is Command.HideLoadingDialog -> swipeLayout.isRefreshing = false
            is Command.ShowLoadingDialog -> swipeLayout.isRefreshing = true
        }
    }

    override fun onItemClick(item: RestaurantResponse) {
        launchActivity<DetailRestaurantActivity> {
            putExtra(AppConstant.RESTAURANT, item)
        }
    }

    override fun onRefresh() {
        mViewModel.getListRestaurant()
    }


}