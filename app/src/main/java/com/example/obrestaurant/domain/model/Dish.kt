package com.example.obrestaurant.domain.model

data class Dish(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: String?,
    val rating: String?,
    val cuisineId: Int
)