package com.demorestaurant.di.module

import android.app.Application
import com.demorestaurant.BuildConfig
import com.demorestaurant.data.MockRetrofitInterceptor
import com.demorestaurant.data.config.NetworkConnectionInterceptor
import com.demorestaurant.data.RestaurantApi
import com.demorestaurant.di.rx.AppSchedulerProvider
import com.demorestaurant.di.rx.SchedulerProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setPrettyPrinting()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideGsonConverter(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provideMockRetrofitInterceptor(): MockRetrofitInterceptor {
        return MockRetrofitInterceptor()
    }

    @Provides
    @Singleton
    fun provideNetWorkConnectionInterceptor(application: Application): NetworkConnectionInterceptor {
        return NetworkConnectionInterceptor(
            application
        )
    }

    @Provides
    @Singleton
    fun provideHttpClientMain(
        loggingInterceptor: HttpLoggingInterceptor
        , networkConnectionInterceptor: NetworkConnectionInterceptor
        , mockRetrofitInterceptor: MockRetrofitInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(networkConnectionInterceptor)
            .addInterceptor(mockRetrofitInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitMain(
        okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory
        , callAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): RestaurantApi =
        retrofit.create(RestaurantApi::class.java)

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }
}