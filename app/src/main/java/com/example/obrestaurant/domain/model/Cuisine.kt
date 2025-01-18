package com.example.obrestaurant.domain.model

data class Cuisine(
    val id: String,
    val name: String,
    val imageUrl: String,
    val dishes: List<Dish>
)