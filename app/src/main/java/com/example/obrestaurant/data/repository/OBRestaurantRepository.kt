package com.example.obrestaurant.data.repository

import com.example.obrestaurant.data.remote.api.RestaurantApi
import com.example.obrestaurant.data.remote.api.dto.FilterRequest
import com.example.obrestaurant.data.remote.api.dto.ItemListRequest
import com.example.obrestaurant.data.remote.api.dto.ItemRequest
import com.example.obrestaurant.data.remote.api.dto.PaymentItem
import com.example.obrestaurant.data.remote.api.dto.PaymentRequest
import com.example.obrestaurant.data.remote.api.dto.PriceRange
import com.example.obrestaurant.data.remote.mapper.toDomain
import com.example.obrestaurant.domain.model.CartItem
import com.example.obrestaurant.domain.model.Cuisine
import com.example.obrestaurant.domain.model.Dish
import javax.inject.Inject

class OBRestaurantRepository @Inject constructor(
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

    suspend fun getItemById(itemId: Int): Result<Dish> =
        try {
            val response = api.getItemById(ItemRequest(itemId))
            if (response.responseCode == 200) {
                Result.success(
                    Dish(
                        id = response.itemId,
                        name = response.itemName,
                        imageUrl = response.itemImageUrl,
                        price = response.itemPrice.toString(),
                        rating = response.itemRating.toString(),
                        cuisineId = response.cuisineId.toIntOrNull() ?: 1,
                    )
                )
            } else {
                Result.failure(Exception(response.responseMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    suspend fun makePayment(
        totalAmount: Double,
        items: List<CartItem>
    ): Result<String> = try {
        val request = PaymentRequest(
            totalAmount = totalAmount.toString(),
            totalItems = items.sumOf { it.quantity },
            data = items.map { item ->
                PaymentItem(
                    cuisineId = item.cuisineId ?: 0,
                    itemId = item.itemId,
                    itemPrice = item.price?.toInt() ?: 0,
                    itemQuantity = item.quantity
                )
            }
        )

        val response = api.makePayment(request)
        if (response.responseCode == 200) {
            Result.success(response.txnRefNo)
        } else {
            Result.failure(Exception(response.responseMessage))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}