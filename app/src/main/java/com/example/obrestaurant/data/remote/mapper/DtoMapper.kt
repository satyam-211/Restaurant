package com.example.obrestaurant.data.remote.mapper

import com.example.obrestaurant.data.remote.api.dto.CuisineDto
import com.example.obrestaurant.data.remote.api.dto.DishDto
import com.example.obrestaurant.domain.model.Cuisine
import com.example.obrestaurant.domain.model.Dish

fun CuisineDto.toDomain() = Cuisine(
    id = cuisineId,
    name = cuisineName,
    imageUrl = cuisineImageUrl,
    dishes = items.map { it.toDomain() }
)

fun DishDto.toDomain() = Dish(
    id = id,
    name = name,
    imageUrl = imageUrl,
    price = price.toDoubleOrNull() ?: 0.0,
    rating = rating.toFloatOrNull() ?: 0f
)