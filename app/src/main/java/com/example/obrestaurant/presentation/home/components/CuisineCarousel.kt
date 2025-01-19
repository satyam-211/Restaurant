package com.example.obrestaurant.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.obrestaurant.domain.model.Cuisine

@Composable
fun CuisineCarousel(
    cuisines: List<Cuisine>,
    onCuisineClick: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(cuisines) { cuisine ->
            CuisineCard(
                cuisine = cuisine,
                onClick = { onCuisineClick(cuisine.id) }
            )
        }
    }
}

