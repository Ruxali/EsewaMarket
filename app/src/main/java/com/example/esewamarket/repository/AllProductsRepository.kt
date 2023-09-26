package com.example.esewamarket.repository

import com.example.esewamarket.api.ApiService
import com.example.esewamarket.models.ProductsItem

class AllProductsRepository (private val apiService: ApiService) {

    suspend fun getAllProducts(limit: String, sort: String) : ArrayList<ProductsItem>?{
        val allProductsResult = apiService.getAllProducts(limit, sort)
        return allProductsResult.body()
    }
}