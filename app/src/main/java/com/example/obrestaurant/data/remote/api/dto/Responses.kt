package com.example.obrestaurant.data.remote.api.dto

import com.google.gson.annotations.SerializedName

data class ItemListResponse(
    @SerializedName("response_code")
    val responseCode: Int,
    @SerializedName("outcome_code")
    val outcomeCode: Int,
    @SerializedName("response_message")
    val responseMessage: String,
    val page: Int,
    val count: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_items")
    val totalItems: Int,
    val cuisines: List<CuisineDto>
)

data class CuisineDto(
    @SerializedName("cuisine_id")
    val cuisineId: Int,
    @SerializedName("cuisine_name")
    val cuisineName: String,
    @SerializedName("cuisine_image_url")
    val cuisineImageUrl: String,
    val items: List<DishDto>
)

data class DishDto(
    val id: Int,
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String,
    val price: String?,
    val rating: String?
)

data class FilterResponse(
    @SerializedName("response_code")
    val responseCode: Int,
    @SerializedName("outcome_code")
    val outcomeCode: Int,
    @SerializedName("response_message")
    val responseMessage: String,
    val cuisines: List<CuisineDto>
)

data class PaymentResponse(
    @SerializedName("response_code")
    val responseCode: Int,
    @SerializedName("outcome_code")
    val outcomeCode: Int,
    @SerializedName("response_message")
    val responseMessage: String,
    @SerializedName("txn_ref_no")
    val txnRefNo: String
)

data class ItemResponse(
    @SerializedName("response_code")
    val responseCode: Int,
    @SerializedName("outcome_code")
    val outcomeCode: Int,
    @SerializedName("response_message")
    val responseMessage: String,
    @SerializedName("cuisine_id")
    val cuisineId: String,
    @SerializedName("cuisine_name")
    val cuisineName: String,
    @SerializedName("item_id")
    val itemId: Int,
    @SerializedName("item_name")
    val itemName: String,
    @SerializedName("item_price")
    val itemPrice: Float,
    @SerializedName("item_rating")
    val itemRating: Float,
    @SerializedName("item_image_url")
    val itemImageUrl: String
)