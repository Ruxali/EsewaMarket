package com.example.esewamarket.models

data class CartItems(
    val category: String,
    val id: Int,
    val image: String,
    var price: Double,
    var changedPrice: Double,
    var totalAmount: Double,
    val title: String,
    var quantity: Int
) {

}