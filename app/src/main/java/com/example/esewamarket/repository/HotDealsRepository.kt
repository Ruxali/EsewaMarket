package com.example.esewamarket.repository

import com.example.esewamarket.api.ApiService
import com.example.esewamarket.models.ProductsItem

class HotDealsRepository (private val apiService: ApiService) {

    suspend fun getHotDeals(limit: String) : ArrayList<ProductsItem>?{
        val hotDealsResult = apiService.getHotDeals(limit)
        return hotDealsResult.body()
    }

}
