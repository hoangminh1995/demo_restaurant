package com.demorestaurant.di.builder

import com.demorestaurant.di.scope.ActivityScope
import com.demorestaurant.ui.MainActivity
import com.demorestaurant.ui.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindMainActivity(): MainActivity
}