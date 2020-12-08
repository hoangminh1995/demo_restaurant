package com.demorestaurant


import com.demorestaurant.di.component.ApplicationComponent
import com.demorestaurant.di.component.DaggerApplicationComponent
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

class RestaurantApplication : DaggerApplication() {
    private lateinit var mApplicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        globalErrorRxJava()
        mApplicationComponent.inject(this)
        if (BuildConfig.DEBUG) {
            setUpForDebug()
        }
    }

    private fun setUpForDebug() {
        Timber.plant(Timber.DebugTree())
        Stetho.initializeWithDefaults(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
        return mApplicationComponent
    }

    private fun globalErrorRxJava() {
        // Global handle error RxJava2 ( when miss onError )
        RxJavaPlugins.setErrorHandler { t ->
            Timber.e("Global handle error ${t.message}")
        }
    }

}