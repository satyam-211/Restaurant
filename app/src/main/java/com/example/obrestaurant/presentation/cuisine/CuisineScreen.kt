package com.example.obrestaurant.presentation.cuisine

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.obrestaurant.presentation.common.ErrorView
import com.example.obrestaurant.presentation.cuisine.components.CuisineDishList
import com.example.obrestaurant.presentation.cuisine.state.CuisineScreenEvent
import com.example.obrestaurant.presentation.cuisine.state.CuisineScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CuisineScreen(
    state: CuisineScreenState,
    onEvent: (CuisineScreenEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.cuisineName) },
                navigationIcon = {
                    IconButton(onClick = { onEvent(CuisineScreenEvent.OnBackClick) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { onEvent(CuisineScreenEvent.OnCartClick) }) {
                        Icon(Icons.Default.ShoppingCart, "Cart")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.isLoading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                state.error != null -> ErrorView(
                    error = state.error,
                    onRetryClick = { onEvent(CuisineScreenEvent.RetryClick) }
                )
                else -> CuisineDishList(
                    dishes = state.dishes,
                    onAddToCart = { dish, quantity ->
                        onEvent(CuisineScreenEvent.OnAddDishToCart(dish, quantity))
                    }
                )
            }
        }
    }
}