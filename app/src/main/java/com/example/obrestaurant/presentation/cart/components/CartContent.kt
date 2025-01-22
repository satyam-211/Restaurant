package com.example.obrestaurant.presentation.cart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.obrestaurant.presentation.cart.state.CartScreenEvent
import com.example.obrestaurant.presentation.cart.state.CartScreenState

@Composable
fun CartContent(
    state: CartScreenState,
    onEvent: (CartScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        // Cart items list
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.items) { item ->
                CartItemRow(
                    item = item,
                    onQuantityChange = { quantity ->
                        onEvent(CartScreenEvent.OnUpdateQuantity(item.itemId, quantity))
                    },
                    onRemove = {
                        onEvent(CartScreenEvent.OnRemoveItem(item.itemId))
                    }
                )
            }
        }

        // Order summary
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OrderSummaryRow("Net Total", "₹${state.netTotal}")
                OrderSummaryRow("CGST (2.5%)", "₹${state.tax / 2}")
                OrderSummaryRow("SGST (2.5%)", "₹${state.tax / 2}")
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                OrderSummaryRow(
                    "Grand Total",
                    "₹${state.grandTotal}",
                    TextStyle(fontWeight = FontWeight.Bold)
                )
            }
        }

        // Place order button
        Button(
            onClick = { onEvent(CartScreenEvent.OnPlaceOrder) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            enabled = state.items.isNotEmpty()
        ) {
            Text("Place Order")
        }
    }
}