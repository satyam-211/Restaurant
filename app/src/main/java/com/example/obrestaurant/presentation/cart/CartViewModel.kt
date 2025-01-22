package com.example.obrestaurant.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.obrestaurant.data.repository.OBRestaurantRepository
import com.example.obrestaurant.domain.manager.CartManager
import com.example.obrestaurant.domain.model.CartItem
import com.example.obrestaurant.presentation.cart.state.CartScreenEvent
import com.example.obrestaurant.presentation.cart.state.CartScreenState
import com.example.obrestaurant.presentation.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: OBRestaurantRepository,
    private val cartManager: CartManager
) : ViewModel() {

    private val _state = MutableLiveData(CartScreenState())
    val state: LiveData<CartScreenState> = _state

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            val itemIds = cartManager.cartItemIds
            if (itemIds.isNotEmpty()) {
                fetchCartItems(itemIds)
            } else {
                _state.value = _state.value?.copy(
                    items = emptyList(),
                    netTotal = 0.0,
                    tax = 0.0,
                    grandTotal = 0.0
                )
            }
        }
    }

    private suspend fun fetchCartItems(items: List<CartItem>) {
        _state.value = _state.value?.copy(isLoading = true)

        try {
            val deferredItems = items.map { cartItem ->
                viewModelScope.async {
                    repository.getItemById(cartItem.itemId).map { dish ->
                        CartItem(
                            cuisineId = dish.cuisineId,
                            itemId = cartItem.itemId,
                            price = dish.price?.toFloatOrNull() ?: 0f,
                            quantity = cartItem.quantity,
                        )
                    }
                }
            }

            // Wait for all requests to complete
            val results = deferredItems.awaitAll()

            // Filter successful results
            val cartItems = results.mapNotNull { it.getOrNull() }

            if (cartItems.isEmpty()) {
                _state.value = _state.value?.copy(
                    isLoading = false,
                    error = "Unknown error occurred"
                )
            } else {
                // Calculate totals
                val netTotal = calculateNetTotal(cartItems)
                val tax = calculateTax(netTotal)
                val grandTotal = netTotal + tax

                _state.value = _state.value?.copy(
                    isLoading = false,
                    items = cartItems,
                    netTotal = netTotal,
                    tax = tax,
                    grandTotal = grandTotal,
                    error = null
                )
            }


        } catch (e: Exception) {
            _state.value = _state.value?.copy(
                isLoading = false,
                error = e.message ?: "Unknown error occurred"
            )
        }
    }

    fun onEvent(cartScreenEvent: CartScreenEvent) {
        when (cartScreenEvent) {
            is CartScreenEvent.OnUpdateQuantity -> {
                cartManager.updateQuantity(cartScreenEvent.itemId, cartScreenEvent.quantity)
                if (cartScreenEvent.quantity == 0) {
                    onEvent(CartScreenEvent.OnRemoveItem(cartScreenEvent.itemId))
                } else {
                    _state.value?.items?.let { currentItems ->
                        val updatedItems = currentItems.map { item ->
                            if (item.itemId == cartScreenEvent.itemId) {
                                item.copy(quantity = cartScreenEvent.quantity)
                            } else item
                        }
                        updateCartState(updatedItems)
                    }
                }

            }

            is CartScreenEvent.OnRemoveItem -> {
                cartManager.removeItem(cartScreenEvent.itemId)
                _state.value?.items?.let { currentItems ->
                    val updatedItems = currentItems.filter { it.itemId != cartScreenEvent.itemId }
                    updateCartState(updatedItems)
                }
                viewModelScope.launch {
                    _event.emit(Event.Toast("Item Removed: ${cartScreenEvent.itemId}"))
                }
            }

            CartScreenEvent.OnPlaceOrder -> placeOrder()
            CartScreenEvent.OnBackClick -> {
                viewModelScope.launch {
                    _event.emit(Event.Navigation("back"))
                }
            }

            CartScreenEvent.OnRetry -> viewModelScope.launch {
                fetchCartItems(cartManager.cartItemIds)
            }
        }
    }

    private fun updateCartState(items: List<CartItem>) {
        val netTotal = calculateNetTotal(items)
        val tax = calculateTax(netTotal)
        val grandTotal = netTotal + tax

        _state.value = _state.value?.copy(
            isLoading = false,
            items = items,
            netTotal = netTotal,
            tax = tax,
            grandTotal = grandTotal,
            error = null
        )
    }

    private fun placeOrder() {
        val currentState = _state.value ?: return
        if (currentState.items.isEmpty()) return
        viewModelScope.launch {
            _state.value = _state.value?.copy(isLoading = true)
            try {
                val result = repository.makePayment(
                    totalAmount = currentState.grandTotal,
                    items = currentState.items
                )

                result.fold(
                    onSuccess = { txnRefNo ->
                        _state.value = currentState.copy(
                            isLoading = false,
                            isOrderSuccess = true,
                            transactionId = txnRefNo,
                            error = null
                        )
                        cartManager.clearCart()
                        updateCartState(mutableListOf())
                        _event.emit(Event.Toast("Order placed successfully! Transaction ID: $txnRefNo"))
                        delay(1000)
                        _event.emit(Event.Navigation("back"))
                    },
                    onFailure = { error ->
                        _state.value = currentState.copy(
                            isLoading = false,
                            error = error.message ?: "Payment failed"
                        )
                        _event.emit(Event.Toast("Failed to place order: ${error.message}"))
                    },
                )
                cartManager.clearCart()
            } catch (e: Exception) {
                _state.value = _state.value?.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
                _event.emit(Event.Toast("Failed to place order: ${e.message}"))
            }
        }
    }

    private fun calculateNetTotal(items: List<CartItem>): Double {
        return items.sumOf { (it.price?.toDouble() ?: 0.0) * it.quantity }
    }

    private fun calculateTax(netTotal: Double): Double {
        return netTotal * 0.05 // CGST + SGST = 5%
    }
}