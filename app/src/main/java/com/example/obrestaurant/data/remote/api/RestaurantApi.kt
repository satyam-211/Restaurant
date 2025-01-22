package com.example.obrestaurant.data.remote.api

import com.example.obrestaurant.data.remote.api.dto.FilterRequest
import com.example.obrestaurant.data.remote.api.dto.FilterResponse
import com.example.obrestaurant.data.remote.api.dto.ItemListRequest
import com.example.obrestaurant.data.remote.api.dto.ItemListResponse
import com.example.obrestaurant.data.remote.api.dto.ItemRequest
import com.example.obrestaurant.data.remote.api.dto.ItemResponse
import com.example.obrestaurant.data.remote.api.dto.PaymentRequest
import com.example.obrestaurant.data.remote.api.dto.PaymentResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RestaurantApi {

    @POST("/emulator/interview/get_item_list")
    suspend fun getItemList(
        @Body request: ItemListRequest
    ): ItemListResponse

    @POST("/emulator/interview/get_item_by_filter")
    suspend fun getItemByFilter(
        @Body request: FilterRequest
    ): FilterResponse

    @POST("/emulator/interview/make_payment")
    suspend fun makePayment(
        @Body request: PaymentRequest
    ): PaymentResponse

    @POST("/emulator/interview/get_item_by_id")
    suspend fun getItemById(
        @Body request: ItemRequest
    ): ItemResponse

}