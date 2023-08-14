package com.automationtest.composeuitest.data

import com.automationtest.composeuitest.testdata.ProductItems

data class TestData(
    val testAccount: Account,    // Account object for login
    val testAddress: Address,    // Address object for input during purchase
    var testProducts: List<Product>, // List of products to add to the cart
    var testCartQuantity: Int    // Expected quantity of items in the cart
)

data class Account(
    val username: String,       // Username for login
    val password: String        // Password for login
)

data class Address(
    val firstName: String,      // First name for address
    val lastName: String,       // Last name for address
    val zipCode: String         // ZIP code for address
)

data class Product(
    val name: String,           // Product name
    val index: Int              // Index of the product in the list
)

val testAccount = Account(username = "john_doe", password = "password123")
val testAddress = Address(firstName = "john", lastName = "doe", zipCode = "123-4567")
val testProducts = listOf(
    Product(name = ProductItems.PRODUCT_LIST[0], index = 0),
    Product(name = ProductItems.PRODUCT_LIST[1], index = 1))
var testCartQuantity = testProducts.size // Initialized to the size of the product list