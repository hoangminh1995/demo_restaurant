package com.demorestaurant.ui.listrestaurant

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
import javax.inject.Inject

class ListRestaurantActivity : DaggerAppCompatActivity(), RestaurantAdapter.CallbackListRestaurant {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var mViewModel: ListRestaurantViewModel

    private lateinit var mRestaurantAdapter: RestaurantAdapter

    private lateinit var mBinding: ActivityListRestaurantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_list_restaurant)
        mViewModel = ViewModelProvider(this, factory).get(ListRestaurantViewModel::class.java)
        subscribeLiveData()
        setUpUI()
        mViewModel.getListRestaurant()
    }

    private fun setUpUI() {
        mBinding.apply {
            mRestaurantAdapter = RestaurantAdapter(this@ListRestaurantActivity)
            rcvListRestaurant.adapter = mRestaurantAdapter
            setSupportActionBar(toolbar)
            swipeLayout.setOnRefreshListener {
                mViewModel.getListRestaurant()
            }
        }

    }

    private fun subscribeLiveData() {
        mViewModel.mListRestaurant.observe(this, Observer { listRestaurantResponse ->
            mRestaurantAdapter.submitList(listRestaurantResponse)
        })

        mViewModel.mCommand.observe(this, Observer {
            handleCommand(it)
        })
    }

    private fun handleCommand(command: Command) {
        when (command) {
            is Command.OfflineDialog -> Toast.makeText(
                this,
                getString(R.string.please_check_internet),
                Toast.LENGTH_LONG
            ).show()
            is Command.HideLoadingDialog -> mBinding.swipeLayout.isRefreshing = false
            is Command.ShowLoadingDialog -> mBinding.swipeLayout.isRefreshing = true
        }
    }

    override fun onItemClick(item: RestaurantResponse) {
        launchActivity<DetailRestaurantActivity> {
            putExtra(AppConstant.RESTAURANT, item)
        }
    }

}