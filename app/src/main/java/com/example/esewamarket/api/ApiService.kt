package com.example.esewamarket.api

import com.example.esewamarket.models.ProductsItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //For Categories
    @GET("products/categories")
    suspend fun getCategories() : Response<List<String>>

    //For Featured Products
    @GET("products")
    suspend fun getFeaturedProducts(@Query("limit") limit: String) :Response<ArrayList<ProductsItem>>

    //For Hot Deals Products
    @GET("products/category/electronics")
    suspend fun getHotDeals(@Query("limit") limit: String) : Response<ArrayList<ProductsItem>>

    //For Popular Brands
    @GET("products/category/jewelery")
    suspend fun getPopularBrand(@Query("limit") limit: String) : Response<ArrayList<ProductsItem>>

    //For All Products
    @GET("products")
    suspend fun getAllProducts(@Query("limit") limit: String, @Query("sort") sort: String) : Response<ArrayList<ProductsItem>>
}