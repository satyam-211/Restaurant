package com.example.obrestaurant.data.remote.mapper

import com.example.obrestaurant.data.remote.api.dto.CuisineDto
import com.example.obrestaurant.data.remote.api.dto.DishDto
import com.example.obrestaurant.domain.model.Cuisine
import com.example.obrestaurant.domain.model.Dish

fun CuisineDto.toDomain() = Cuisine(
    id = cuisineId,
    name = cuisineName,
    imageUrl = cuisineImageUrl,
    dishes = items.map { it.toDomain(cuisineId = cuisineId) }
)

fun DishDto.toDomain(cuisineId: Int) = Dish(
    id = id,
    name = name,
    imageUrl = imageUrl,
    cuisineId = cuisineId,
    price = price,
    rating = rating,
)