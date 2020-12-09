package com.demorestaurant.ui.detailrestaurant

import androidx.lifecycle.MutableLiveData
import com.demorestaurant.data.config.NetworkConnectionInterceptor
import com.demorestaurant.data.remote.DetailRestaurantResponse
import com.demorestaurant.data.remote.RestaurantResponse
import com.demorestaurant.repository.RestaurantRepository
import com.demorestaurant.ui.base.BaseViewModel
import com.demorestaurant.ui.base.Command
import com.demorestaurant.ui.listrestaurant.ListRestaurantViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DetailRestaurantViewModel @Inject constructor(
    private val restaurantRepository: RestaurantRepository
) : BaseViewModel() {
    lateinit var mRestaurantResponse: RestaurantResponse
    val mListDetailRestaurant: MutableLiveData<List<DetailRestaurantResponse>> = MutableLiveData()

    fun getDetailRestaurant() {
        setCommand(Command.ShowLoadingDialog())
        restaurantRepository.getDetailRestaurantRemote(mRestaurantResponse.id)
            .subscribeOn(mSchedulerProvider.io())
            .delay(ListRestaurantViewModel.DELAY_TIME_SECOND, TimeUnit.SECONDS)
            .observeOn(mSchedulerProvider.ui())
            .subscribe({ response ->
                setCommand(Command.HideLoadingDialog())
                mListDetailRestaurant.value = response
            }, { t ->
                setCommand(Command.HideLoadingDialog())
                when (t) {
                    is NetworkConnectionInterceptor.NoConnectivityException -> setCommand(Command.OfflineDialog())
                    else -> handleError(t)
                }
            }).let { d -> mCompositeDisposable.add(d) }
    }
}