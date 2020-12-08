package com.demorestaurant.ui

import com.demorestaurant.di.scope.FragmentScope
import com.demorestaurant.ui.detailrestaurant.DetailRestaurantFragment
import com.demorestaurant.ui.listrestaurant.ListRestaurantFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bindListRestaurantFragment(): ListRestaurantFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bindDetailRestaurantFragment(): DetailRestaurantFragment

}