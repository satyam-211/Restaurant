package com.example.obrestaurant.data.repository

import com.example.obrestaurant.data.remote.api.RestaurantApi
import com.example.obrestaurant.data.remote.api.dto.FilterRequest
import com.example.obrestaurant.data.remote.api.dto.ItemListRequest
import com.example.obrestaurant.data.remote.api.dto.PriceRange
import com.example.obrestaurant.data.remote.mapper.toDomain
import com.example.obrestaurant.domain.model.Cuisine
import javax.inject.Inject

class CuisineRepository @Inject constructor(
    private val api: RestaurantApi
) {

    suspend fun getItemList(page: Int, count: Int): Result<List<Cuisine>> =
        try {
            val response = api.getItemList(ItemListRequest(page, count))
            if (response.responseCode == 200) {
                Result.success(response.cuisines.map { it.toDomain() })
            } else {
                Result.failure(Exception(response.responseMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    suspend fun getItemsByFilter(
        cuisineTypes: List<String>?,
        priceRange: PriceRange? = null,
        minRating: Float? = null,
    ): Result<List<Cuisine>> =
        try {
            val response = api.getItemByFilter(
                FilterRequest(
                    cuisineType = cuisineTypes,
                    priceRange = priceRange,
                    minRating = minRating
                )
            )
            if (response.responseCode == 200) {
                Result.success(response.cuisines.map { it.toDomain() })
            } else {
                Result.failure(Exception(response.responseMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
}