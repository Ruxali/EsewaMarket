package com.example.esewamarket.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.esewamarket.interfaces.CartDataSources
import com.example.esewamarket.viewModels.ProductDetailsViewModel

class ProductDetailsViewModelFactory  (private val cartDataSource: CartDataSources): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)) {
            return ProductDetailsViewModel(cartDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}