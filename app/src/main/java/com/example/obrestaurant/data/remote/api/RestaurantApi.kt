package com.example.obrestaurant.data.remote.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RestaurantApi {

    @GET("/emulator/interview/get_item_list")
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

}