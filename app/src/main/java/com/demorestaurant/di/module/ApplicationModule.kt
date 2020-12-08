package com.demorestaurant.di.module

import android.app.Application
import android.content.Context
import com.demorestaurant.di.qualifier.ApplicationContext
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ApplicationModule {
    @Singleton
    @Binds
    @ApplicationContext
    abstract fun provideContext(app: Application): Context

}