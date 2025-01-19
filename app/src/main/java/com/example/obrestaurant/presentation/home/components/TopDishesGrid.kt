package com.example.obrestaurant.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.obrestaurant.domain.model.Dish
import kotlin.math.ceil


@Composable
fun TopDishesGrid(
    dishes: List<Dish>,
    onAddToCart: (Dish, Int) -> Unit,
    modifier: Modifier,
) {
    LazyVerticalGrid(
        modifier = modifier.height(height = (ceil(dishes.size / 2f) * 200).dp),
        columns = GridCells.Fixed(2),
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