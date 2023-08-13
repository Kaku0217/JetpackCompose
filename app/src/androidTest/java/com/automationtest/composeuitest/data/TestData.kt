package com.automationtest.composeuitest.data

import com.automationtest.composeuitest.testdata.ProductItems

data class TestData(
    val testAccount: Account,
    val testAddress: Address,
    var testProducts: List<Product>,
    var testCartQuantity: Int
)

data class Account(
    val username: String,
    val password: String
)

data class Address(
    val firstName: String,
    val lastName: String,
    val zipCode: String
)

data class Product(
    val name: String,
    val index: Int
)

val testAccount = Account(username = "john_doe", password = "password123")
val testAddress = Address(firstName = "john", lastName = "doe", zipCode = "123-4567")
val testProducts = listOf(
    Product(name = ProductItems.PRODUCT_LIST[0], index = 0),
    Product(name = ProductItems.PRODUCT_LIST[1], index = 1))
var testCartQuantity = testProducts.size

