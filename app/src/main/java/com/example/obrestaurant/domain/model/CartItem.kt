package com.example.obrestaurant.domain.model

data class CartItem(
    val cuisineId: Int? = null,
    val itemId: Int,
    val price: Float? = null,
    val quantity: Int
)