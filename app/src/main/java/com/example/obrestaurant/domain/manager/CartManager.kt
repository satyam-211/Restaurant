package com.example.obrestaurant.domain.manager

import com.example.obrestaurant.domain.model.CartItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartManager @Inject constructor() {
    val cartItemIds = mutableListOf<CartItem>()


    fun addItem(itemId: Int, quantity: Int = 1) {
        val existingItem = cartItemIds.find { it.itemId == itemId }

        if (existingItem != null) {
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + quantity)
            cartItemIds[cartItemIds.indexOf(existingItem)] = updatedItem
        } else {
            cartItemIds.add(CartItem(itemId = itemId, quantity = quantity))
        }
    }

    fun updateQuantity(itemId: Int, quantity: Int) {
        val existingItem = cartItemIds.find { it.itemId == itemId }

        existingItem?.let {
            val updatedItem = it.copy(quantity = quantity)
            cartItemIds[cartItemIds.indexOf(it)] = updatedItem
        }
    }

    fun removeItem(itemId: Int) {
        cartItemIds.removeIf { it.itemId == itemId }
    }

    fun clearCart() {
        cartItemIds.clear()
    }
}