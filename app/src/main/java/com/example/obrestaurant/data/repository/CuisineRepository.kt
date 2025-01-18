package com.example.obrestaurant.data.repository

import com.example.obrestaurant.data.remote.api.RestaurantApi
import javax.inject.Inject

class CuisineRepositoryImpl @Inject constructor(
    private val api: RestaurantApi
) : CuisineRepository {

    override suspend fun getItemList(page: Int, count: Int): Result<List<Cuisine>> =
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

    override suspend fun getItemsByFilter(
        cuisineTypes: List<String>?,
        priceRange: PriceRange?,
        minRating: Float?
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