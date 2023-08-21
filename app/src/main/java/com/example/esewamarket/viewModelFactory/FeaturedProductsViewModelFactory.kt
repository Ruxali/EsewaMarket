package com.example.esewamarket.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.esewamarket.repository.FeaturedProductsRepository
import com.example.esewamarket.viewModels.FeaturedProductsViewModel

class FeaturedProductsViewModelFactory (private val productsRepository: FeaturedProductsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FeaturedProductsViewModel(productsRepository) as T
    }
}