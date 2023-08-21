package com.example.esewamarket.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.esewamarket.repository.AllProductsRepository
import com.example.esewamarket.viewModels.AllProductsViewModel

class AllProductsViewModelFactory (private val allProductsRepository: AllProductsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AllProductsViewModel(allProductsRepository) as T
    }
}