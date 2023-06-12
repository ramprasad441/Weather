package com.ramprasad.weather.di

import com.ramprasad.weatherapp.network.RetrofitClient
import com.ramprasad.weather.repository.WeatherDataRepository
import com.ramprasad.weather.repository.WeatherDataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Ramprasad on 6/11/23.
 */

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideNetworkService(okHttpClient: OkHttpClient): RetrofitClient = Retrofit.Builder()
        .baseUrl(RetrofitClient.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(RetrofitClient::class.java)

    @Provides
    @Singleton
    fun providesWeatherDataRepository(retrofitClient: RetrofitClient): WeatherDataRepository =
        WeatherDataRepositoryImpl(retrofitClient)

    @Provides
    @Singleton
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}