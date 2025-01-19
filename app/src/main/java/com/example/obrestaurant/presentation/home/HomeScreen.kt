package com.example.obrestaurant.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.obrestaurant.presentation.common.ErrorView
import com.example.obrestaurant.presentation.home.components.HomeContent
import com.example.obrestaurant.presentation.home.components.HomeTopBar
import com.example.obrestaurant.presentation.home.state.HomeScreenEvent
import com.example.obrestaurant.presentation.home.state.HomeScreenState

@Composable
fun HomeScreen(
    state: HomeScreenState,
    onEvent: (HomeScreenEvent) -> Unit
){

    Scaffold(
        topBar = {
            HomeTopBar(
                selectedLanguage = state.selectedLanguage,
                onLanguageChange = { language ->
                    onEvent(HomeScreenEvent.OnLanguageChange(language))
                },
                onCartClick = {
                    onEvent(HomeScreenEvent.OnCartClick)
                }
            )
        }
    ){ paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.error != null) {
                ErrorView(
                    error = state.error,
                    onRetryClick = {
                        onEvent(HomeScreenEvent.RetryClick)
                    }
                )
            } else {
                HomeContent(
                    cuisines = state.cuisines,
                    topDishes = state.topDishes,
                    onCuisineClick = { cuisineId ->
                        onEvent(HomeScreenEvent.OnCuisineClick(cuisineId))
                    },
                    onAddDishToCart = { dish, quantity ->
                        onEvent(HomeScreenEvent.OnAddDishToCart(dish, quantity))
                    }
                )
            }
        }
    }
}