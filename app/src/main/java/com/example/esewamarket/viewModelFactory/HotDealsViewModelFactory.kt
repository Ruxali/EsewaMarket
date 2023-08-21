package com.example.esewamarket.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.esewamarket.repository.HotDealsRepository
import com.example.esewamarket.viewModels.HotDealsViewModel

class HotDealsViewModelFactory (private val hotDealsRepository: HotDealsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HotDealsViewModel(hotDealsRepository) as T
    }
}