package com.example.esewamarket.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.esewamarket.interfaces.CartDataSources
import com.example.esewamarket.repository.CartRepository
import com.example.esewamarket.viewModels.CartViewModel
import com.example.esewamarket.viewModels.ProductDetailsViewModel

class CartViewModelFactory  (private val cartDataSource: CartDataSources): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(cartDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}