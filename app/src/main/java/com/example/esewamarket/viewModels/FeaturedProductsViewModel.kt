package com.example.esewamarket.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esewamarket.models.ProductsItem
import com.example.esewamarket.repository.FeaturedProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FeaturedProductsViewModel(private val featuredProductsRepository: FeaturedProductsRepository): ViewModel() {

    val featuredProductsLiveData by lazy { MutableLiveData<ArrayList<ProductsItem>>() }

    init {
        viewModelScope.launch(Dispatchers.IO ){
            featuredProductsLiveData.postValue(featuredProductsRepository.getFeaturedProducts("2"))
        }
    }

}