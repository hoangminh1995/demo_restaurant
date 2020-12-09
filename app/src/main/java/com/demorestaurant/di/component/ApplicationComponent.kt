package com.demorestaurant.di.component

import android.app.Application
import com.demorestaurant.RestaurantApplication
import com.demorestaurant.di.builder.ActivityBuilder
import com.demorestaurant.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class
    , ApplicationModule::class
    , ApiModule::class
    , ActivityBuilder::class
    , ViewModelModule::class
    , RepositoryModule::class])
interface ApplicationComponent : AndroidInjector<RestaurantApplication> {

    override fun inject(instance: RestaurantApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}