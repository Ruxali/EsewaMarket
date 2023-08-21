package com.example.esewamarket.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.esewamarket.repository.PopularBrandsRepository
import com.example.esewamarket.viewModels.PopularBrandViewModel

class PopularBrandViewModelFactory(private val popularBrandsRepository: PopularBrandsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PopularBrandViewModel(popularBrandsRepository) as T
    }
}