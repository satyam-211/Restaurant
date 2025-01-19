package com.example.obrestaurant.presentation.home.state

import com.example.obrestaurant.domain.model.Cuisine
import com.example.obrestaurant.domain.model.Dish

data class HomeScreenState(
    val cuisines: List<Cuisine> = emptyList(),
    val topDishes: List<Dish> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedLanguage: Language = Language.ENGLISH
)

enum class Language {
    ENGLISH,
    HINDI
}

sealed class HomeScreenEvent {
    data class OnCuisineClick(val cuisineId: String) : HomeScreenEvent()
    data class OnAddDishToCart(val dish: Dish, val quantity: Int) : HomeScreenEvent()
    data class OnLanguageChange(val language: Language) : HomeScreenEvent()
    data object OnCartClick : HomeScreenEvent()
    data object RetryClick : HomeScreenEvent()
}