package com.example.esewamarket.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.esewamarket.adapters.CartItemAdapter
import com.example.esewamarket.interfaces.CartDataSources
import com.example.esewamarket.models.CartItems
import com.example.esewamarket.models.ProductsItem
import com.example.esewamarket.repository.CartRepository

class CartViewModel(private val cartDataSource: CartDataSources):ViewModel() {

    val cartLiveData = MutableLiveData<List<CartItems>>()

    fun getDataFromProductDetails() {
        cartLiveData.value = cartDataSource.getCartItems()
    }

    fun removeFromCart(id: Int){
        cartDataSource.removeFromCart(id)
    }

    fun increaseQuantity(id: Int){
        cartDataSource.increaseQuantity(id)
    }

    fun decreaseQuantity(id: Int){
        cartDataSource.decreaseQuantity(id)
    }
}