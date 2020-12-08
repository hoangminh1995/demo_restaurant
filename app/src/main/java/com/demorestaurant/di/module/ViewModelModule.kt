package com.demorestaurant.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demorestaurant.di.mapkey.ViewModelKey
import com.demorestaurant.ui.detailrestaurant.DetailRestaurantViewModel
import com.demorestaurant.ui.listrestaurant.ListRestaurantViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ListRestaurantViewModel::class)
    abstract fun bindsListRestaurantViewModel(viewModel: ListRestaurantViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailRestaurantViewModel::class)
    abstract fun bindsDetailRestaurantViewModel(viewModel: DetailRestaurantViewModel): ViewModel

}