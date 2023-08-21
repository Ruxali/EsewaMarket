package com.example.esewamarket.repository

import com.example.esewamarket.api.ApiService
import com.example.esewamarket.models.ProductsItem

class PopularBrandsRepository(private val apiService: ApiService) {


    suspend fun getPopularBrands(limit: String) : ArrayList<ProductsItem>?{
        val popularBrandsResult = apiService.getPopularBrand(limit)
        return popularBrandsResult.body()
    }
}
