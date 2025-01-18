package com.example.obrestaurant.domain.model

data class CartItem(
    val cuisineId: Int,
    val itemId: Int,
    val price: Int,
    val quantity: Int
)