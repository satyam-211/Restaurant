package com.example.obrestaurant.presentation.cuisine.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.obrestaurant.domain.model.Dish
import com.example.obrestaurant.presentation.common.DishCard

@Composable
fun CuisineDishList(
    dishes: List<Dish>,
    onAddToCart: (Dish, Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(dishes) { dish ->
            DishCard(
                dish = dish,
                onAddToCart = onAddToCart
            )
        }
    }
}