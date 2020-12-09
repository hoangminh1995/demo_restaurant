package com.demorestaurant.ui.detailrestaurant

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.demorestaurant.R
import com.demorestaurant.data.remote.RestaurantResponse
import com.demorestaurant.databinding.ActivityDetailRestaurantBinding
import com.demorestaurant.di.module.ViewModelProviderFactory
import com.demorestaurant.ui.base.Command
import com.demorestaurant.ui.detailrestaurant.adapter.DetailRestaurantAdapter
import com.demorestaurant.utils.AppConstant
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class DetailRestaurantActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var mViewModel: DetailRestaurantViewModel

    private lateinit var mBinding: ActivityDetailRestaurantBinding

    private lateinit var mDetailRestaurantAdapter: DetailRestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_restaurant)
        mViewModel = ViewModelProvider(this, factory).get(DetailRestaurantViewModel::class.java)
        mViewModel.mRestaurantResponse =
            intent.getParcelableExtra<RestaurantResponse>(AppConstant.RESTAURANT) as RestaurantResponse

        subscribeLiveData()
        setUpUI()

        mViewModel.getDetailRestaurant()
    }

    private fun setUpUI() {
        mBinding.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.restaurant_working_hours, mViewModel.mRestaurantResponse.name)
            mDetailRestaurantAdapter = DetailRestaurantAdapter()
            rcvDetailRestaurant.adapter = mDetailRestaurantAdapter
            swipeLayout.setOnRefreshListener {
                mViewModel.getDetailRestaurant()
            }
        }
    }

    private fun subscribeLiveData() {
        mViewModel.mListDetailRestaurant.observe(this, Observer { listDetailRestaurantResponse ->
            mDetailRestaurantAdapter.submitList(listDetailRestaurantResponse)
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