package com.example.obrestaurant.di

import com.example.obrestaurant.data.remote.api.RestaurantApi
import com.example.obrestaurant.data.repository.OBRestaurantRepository
import com.example.obrestaurant.domain.manager.CartManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCartManager(): CartManager = CartManager()

    @Provides
    @Singleton
    fun provideCuisineRepository(api: RestaurantApi): OBRestaurantRepository {
        return OBRestaurantRepository(api)
    }
}