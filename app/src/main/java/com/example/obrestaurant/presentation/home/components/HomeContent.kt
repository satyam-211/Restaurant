package com.example.obrestaurant.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.obrestaurant.domain.model.Cuisine
import com.example.obrestaurant.domain.model.Dish

@Composable
fun HomeContent(
    cuisines: List<Cuisine>,
    topDishes: List<Dish>,
    onCuisineClick: (String) -> Unit,
    onAddDishToCart: (Dish, Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Text(
                text = "Cuisines",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        item {
            CuisineCarousel(
                cuisines = cuisines,
                onCuisineClick = onCuisineClick
            )
        }

        item {
            Text(
                text = "Top Dishes",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(start = 16.dp),
            )
        }

        item {
            TopDishesGrid(
                dishes = topDishes,
                onAddToCart = onAddDishToCart,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
        }
    }
}