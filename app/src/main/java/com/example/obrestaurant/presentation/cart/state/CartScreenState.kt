package com.example.obrestaurant.presentation.cart.state

import com.example.obrestaurant.domain.model.CartItem

data class CartScreenState(
    val items: List<CartItem> = emptyList(),
    val netTotal: Double = 0.0,
    val tax: Double = 0.0,
    val grandTotal: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isOrderSuccess: Boolean = false,
    val transactionId: String? = null
)

sealed class CartScreenEvent {
    data class OnUpdateQuantity(val itemId: Int, val quantity: Int) : CartScreenEvent()
    data class OnRemoveItem(val itemId: Int) : CartScreenEvent()
    data object OnPlaceOrder : CartScreenEvent()
    data object OnBackClick : CartScreenEvent()
    data object OnRetry : CartScreenEvent()
}