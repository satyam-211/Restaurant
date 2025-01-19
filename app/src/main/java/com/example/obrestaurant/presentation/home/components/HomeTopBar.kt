package com.example.obrestaurant.presentation.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.example.obrestaurant.presentation.home.state.Language

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    selectedLanguage: Language,
    onLanguageChange: (Language) -> Unit,
    onCartClick: () -> Unit
) {
    TopAppBar(
        title = { Text("Restaurant App") },
        actions = {
            IconButton(
                onClick = {
                    onLanguageChange(
                        if (selectedLanguage == Language.ENGLISH) Language.HINDI
                        else Language.ENGLISH
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Switch Language"
                )
            }
            IconButton(onClick = onCartClick) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart"
                )
            }
        }
    )
}