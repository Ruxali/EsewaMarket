package com.example.esewamarket.repository

import com.example.esewamarket.api.ApiService

class CategoriesRepository (private val apiService: ApiService) {

    suspend fun getCategories(): List<String>?{
        val categoriesResult = apiService.getCategories()
        return categoriesResult.body()
    }
}
