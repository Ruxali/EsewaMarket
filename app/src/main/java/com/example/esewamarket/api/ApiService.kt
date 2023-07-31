package com.example.esewamarket.api

import com.example.esewamarket.models.ProductsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //For Categories
    @GET("products/categories")
    fun getCategories() : Call<List<String>>

    //For Featured Products
    @GET("products")
    fun getFeaturedProducts(@Query("limit") limit: String) : Call<ArrayList<ProductsItem>>

    //For Hot Deals Products
    @GET("products")
    fun getHotDeals(@Query("limit") limit: String, @Query("sort") sort: String) : Call<ArrayList<ProductsItem>>

    //For Popular Brands
    @GET("products/category/jewelery")
    fun getPopularBrand(@Query("limit") limit: String) : Call<ArrayList<ProductsItem>>

    //For Popular Brands
    @GET("products")
    fun getAllProducts(@Query("limit") limit: String) : Call<ArrayList<ProductsItem>>
}