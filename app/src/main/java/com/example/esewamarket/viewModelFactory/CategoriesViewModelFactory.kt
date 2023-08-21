package com.example.esewamarket.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.esewamarket.repository.CategoriesRepository
import com.example.esewamarket.viewModels.CategoriesViewModel

class CategoriesViewModelFactory (private val categoriesRepository: CategoriesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoriesViewModel(categoriesRepository) as T
    }
}