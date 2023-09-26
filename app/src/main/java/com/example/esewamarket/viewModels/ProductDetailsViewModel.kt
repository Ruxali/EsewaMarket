package com.example.esewamarket.viewModels

import androidx.lifecycle.ViewModel
import com.example.esewamarket.interfaces.CartDataSources
import com.example.esewamarket.models.ProductsItem

class ProductDetailsViewModel(private val cartDataSource: CartDataSources):ViewModel() {

    fun addToCart(products: ProductsItem, quantity: Int = 1) {
        cartDataSource.addToCart(products, quantity)
    }
}