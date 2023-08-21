package com.example.esewamarket.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esewamarket.models.ProductsItem
import com.example.esewamarket.repository.HotDealsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HotDealsViewModel (private val hotDealsRepository: HotDealsRepository): ViewModel() {

    val hotDealsLiveData by lazy { MutableLiveData<ArrayList<ProductsItem>>() }


    init {
        viewModelScope.launch(Dispatchers.IO ){
            hotDealsLiveData.postValue(hotDealsRepository.getHotDeals("2"))

        }
    }
}