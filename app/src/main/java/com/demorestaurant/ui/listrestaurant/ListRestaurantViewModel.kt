package com.demorestaurant.ui.listrestaurant

import androidx.lifecycle.MutableLiveData
import com.demorestaurant.data.NetworkConnectionInterceptor
import com.demorestaurant.data.remote.RestaurantResponse
import com.demorestaurant.repository.RestaurantRepository
import com.demorestaurant.ui.base.BaseViewModel
import com.demorestaurant.ui.base.Command
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ListRestaurantViewModel @Inject constructor(
    private val restaurantRepository: RestaurantRepository
) : BaseViewModel() {

    companion object {
        const val DELAY_TIME_SECOND = 2L
    }

    val mListRestaurant: MutableLiveData<List<RestaurantResponse>> = MutableLiveData()

    fun getListRestaurant() {
        setCommand(Command.ShowLoadingDialog())
        restaurantRepository.getListRestaurantRemote()
            .subscribeOn(mSchedulerProvider.io())
            .delay(DELAY_TIME_SECOND, TimeUnit.SECONDS)
            .observeOn(mSchedulerProvider.ui())
            .subscribe({ listRestaurantResponse ->
                setCommand(Command.HideLoadingDialog())
                mListRestaurant.value = listRestaurantResponse
            }, { t ->
                setCommand(Command.HideLoadingDialog())
                when (t) {
                    is NetworkConnectionInterceptor.NoConnectivityException -> setCommand(Command.OfflineDialog())
                    else -> handleError(t)
                }
            }).let { d -> mCompositeDisposable.add(d) }
    }

}