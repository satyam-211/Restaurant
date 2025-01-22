package com.example.obrestaurant.presentation.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.obrestaurant.presentation.cart.components.CartContent
import com.example.obrestaurant.presentation.cart.components.EmptyCartView
import com.example.obrestaurant.presentation.cart.state.CartScreenEvent
import com.example.obrestaurant.presentation.cart.state.CartScreenState
import com.example.obrestaurant.presentation.common.ErrorView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    state: CartScreenState,
    onEvent: (CartScreenEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cart") },
                navigationIcon = {
                    IconButton(onClick = { onEvent(CartScreenEvent.OnBackClick) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
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
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.error != null) {
                ErrorView(
                    error = state.error,
                    onRetryClick = { onEvent(CartScreenEvent.OnRetry) }
                )
            } else if (state.items.isEmpty()) {
                EmptyCartView(modifier = Modifier.align(Alignment.Center))
            } else {
                CartContent(
                    state = state,
                    onEvent = onEvent,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}