package com.example.esewamarket.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esewamarket.api.RetrofitInstance
import com.example.esewamarket.models.ProductsItem
import com.example.esewamarket.repository.FeaturedProductsRepository
import com.example.esewamarket.repository.PopularBrandsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularBrandViewModel(private val popularBrandsRepository: PopularBrandsRepository): ViewModel() {

    val popularBrandsLiveData by lazy { MutableLiveData<ArrayList<ProductsItem>>() }


    init {
        viewModelScope.launch(Dispatchers.IO ){
            popularBrandsLiveData.postValue(popularBrandsRepository.getPopularBrands("2"))

        }
    }

}