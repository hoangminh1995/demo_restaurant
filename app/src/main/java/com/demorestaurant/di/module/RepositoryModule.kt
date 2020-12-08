package com.demorestaurant.di.module

import com.demorestaurant.data.RestaurantApi
import com.demorestaurant.repository.RestaurantRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRestaurantRepository(apiHelper: RestaurantApi): RestaurantRepository {
        return RestaurantRepository(apiHelper)
    }

}