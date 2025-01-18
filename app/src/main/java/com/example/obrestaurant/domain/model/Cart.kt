package com.example.obrestaurant.domain.model

data class Cart(
    val items: List<CartItem>,
    val netTotal: Double,
    val tax: Double,
    val grandTotal: Double
)