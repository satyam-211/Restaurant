package com.example.obrestaurant.data.remote.api.dto

import com.google.gson.annotations.SerializedName

data class ItemListRequest(
    val page: Int,
    val count: Int
)

data class FilterRequest(
    @SerializedName("cuisine_type")
    val cuisineType: List<String>? = null,
    @SerializedName("price_range")
    val priceRange: PriceRange? = null,
    @SerializedName("min_rating")
    val minRating: Float? = null
)

data class PriceRange(
    @SerializedName("min_amount")
    val minAmount: Int,
    @SerializedName("max_amount")
    val maxAmount: Int
)
data class PaymentRequest(
    @SerializedName("total_amount")
    val totalAmount: String,
    @SerializedName("total_items")
    val totalItems: Int,
    val data: List<PaymentItem>
)

data class PaymentItem(
    @SerializedName("cuisine_id")
    val cuisineId: Int,
    @SerializedName("item_id")
    val itemId: Int,
    @SerializedName("item_price")
    val itemPrice: Int,
    @SerializedName("item_quantity")
    val itemQuantity: Int
)


data class ItemRequest(
    @SerializedName("item_id")
    val itemId: Int
)