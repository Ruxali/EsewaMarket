package com.example.esewamarket.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esewamarket.models.ProductsItem
import com.example.esewamarket.repository.AllProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllProductsViewModel (private val allProductsRepository: AllProductsRepository): ViewModel() {

    val allProductsLiveData by lazy { MutableLiveData<ArrayList<ProductsItem>>() }

    init {
        viewModelScope.launch(Dispatchers.IO ){
            allProductsLiveData.postValue(allProductsRepository.getAllProducts("8", "desc"))
        }
    }

}