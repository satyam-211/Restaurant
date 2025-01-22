package com.example.obrestaurant.domain.model

data class Cuisine(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val dishes: List<Dish>
)