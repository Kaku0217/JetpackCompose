package com.automationtest.composeuitest

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel() {
    private val _cartItems = mutableStateListOf<String>()
    val cartItems: List<String> get() = _cartItems

    private val _cartItemCount = mutableStateOf(0)
    val cartItemCount: State<Int> = _cartItemCount

    fun addItemToCart(context: Context, item: String) {
        loadCartItemsFromPrefs(context)
        _cartItems.add(item)
        saveCartItemsToPrefs(context)
        updateCartItemCount(context, _cartItems.size)
    }

    fun loadCartItemsFromPrefs(context: Context) {
        val sharedPreferences = context.getSharedPreferences("CartPrefs", Context.MODE_PRIVATE)
        val itemsSet = sharedPreferences.getStringSet("cartItems", emptySet()) ?: emptySet()
        _cartItems.addAll(itemsSet)
        _cartItemCount.value = _cartItems.size
    }

    private fun saveCartItemsToPrefs(context: Context) {
        val sharedPreferences = context.getSharedPreferences("CartPrefs", Context.MODE_PRIVATE)
        val itemsSet = _cartItems.toSet()
        sharedPreferences.edit().putStringSet("cartItems", itemsSet).apply()
    }

    fun updateCartItemCount(context: Context, itemCount: Int) {
        _cartItemCount.value = itemCount

        val sharedPreferences = context.getSharedPreferences("BadgeCountPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt("badgeCount", itemCount).apply()
    }

    fun getInitialCartItemCount(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences("BadgeCountPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("badgeCount", 0)
    }

    fun clearCart(context: Context) {
        _cartItems.clear()
        _cartItemCount.value = 0

        val sharedPreferences = context.getSharedPreferences("CartPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().remove("cartItems").apply()

        val badgeSharedPreferences = context.getSharedPreferences("BadgeCountPrefs", Context.MODE_PRIVATE)
        badgeSharedPreferences.edit().putInt("badgeCount", 0).apply()
    }

}
