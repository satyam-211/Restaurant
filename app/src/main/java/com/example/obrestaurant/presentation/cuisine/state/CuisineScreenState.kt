package com.example.obrestaurant.presentation.cuisine.state

import com.example.obrestaurant.domain.model.Dish

data class CuisineScreenState(
    val cuisineName: String = "",
    val dishes: List<Dish> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class CuisineScreenEvent {
    data class OnAddDishToCart(val dish: Dish, val quantity: Int) : CuisineScreenEvent()
    data object OnBackClick : CuisineScreenEvent()
    data object OnCartClick : CuisineScreenEvent()
    data object RetryClick : CuisineScreenEvent()
}