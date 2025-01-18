package com.example.obrestaurant.domain.model

data class Dish(
    val id: String,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val rating: Float
)