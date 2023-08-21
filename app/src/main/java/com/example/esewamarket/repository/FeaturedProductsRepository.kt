package com.example.esewamarket.repository

import com.example.esewamarket.api.ApiService
import com.example.esewamarket.models.ProductsItem

class FeaturedProductsRepository (private val apiService: ApiService) {

     suspend fun getFeaturedProducts(limit: String) : ArrayList<ProductsItem>?{
        val featuredProductsResult = apiService.getFeaturedProducts(limit)
        return featuredProductsResult.body()
    }

}
