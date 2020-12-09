package com.demorestaurant.ui.base

import androidx.lifecycle.ViewModel
import com.demorestaurant.BuildConfig
import com.demorestaurant.di.rx.SchedulerProvider
import com.demorestaurant.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var mSchedulerProvider: SchedulerProvider

    @Inject
    lateinit var mCompositeDisposable: CompositeDisposable

    var mCommand: SingleLiveEvent<Command> = SingleLiveEvent()

    fun setCommand(command: Command) {
        mCommand.value = command
    }

    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }

    private fun logException(t: Throwable) {
        if (BuildConfig.DEBUG) {
            Timber.e("Exception: " + t::class.java.simpleName + " " + t.message)
            t.printStackTrace()
        }
    }

    fun handleError(t: Throwable) {
        logException(t)
    }

}