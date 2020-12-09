package com.demorestaurant.di.builder

import com.demorestaurant.di.scope.ActivityScope
import com.demorestaurant.ui.listrestaurant.ListRestaurantActivity
import com.demorestaurant.ui.detailrestaurant.DetailRestaurantActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): ListRestaurantActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindDetailRestaurantActivity(): DetailRestaurantActivity
}