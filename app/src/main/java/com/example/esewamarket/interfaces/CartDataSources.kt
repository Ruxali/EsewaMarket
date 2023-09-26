package com.example.esewamarket.interfaces

import com.example.esewamarket.models.CartItems
import com.example.esewamarket.models.ProductsItem

interface CartDataSources {
    fun getCartItems(): List<CartItems>

    fun addToCart(products: ProductsItem, quantity: Int = 1)

    fun removeFromCart(id:Int)

    fun increaseQuantity(id:Int)

    fun decreaseQuantity(id:Int)
}