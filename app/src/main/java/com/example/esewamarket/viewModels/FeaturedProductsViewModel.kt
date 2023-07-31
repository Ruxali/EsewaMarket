package com.example.esewamarket.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.esewamarket.api.RetrofitInstance
import com.example.esewamarket.models.ProductsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeaturedProductsViewModel: ViewModel() {
    private var featuredProductsLiveData = MutableLiveData<ArrayList<ProductsItem>>()
    private var retrofitInstance = RetrofitInstance

    fun getFeaturedProducts(){
        retrofitInstance.apiInterface.getFeaturedProducts("2").enqueue(object :
            Callback<ArrayList<ProductsItem>>{
            override fun onResponse(
                call: Call<ArrayList<ProductsItem>>,
                response: Response<ArrayList<ProductsItem>>
            ) {
                if (response.body() != null){
                    featuredProductsLiveData.value = response.body()
                }
                else{
                    return
                }

            }
            override fun onFailure(call: Call<ArrayList<ProductsItem>>, t: Throwable) {
                Log.d("TAG",t.message.toString())
            }
            })
    }
    fun observeFeaturedProductsLiveData() : MutableLiveData<ArrayList<ProductsItem>> {
        return featuredProductsLiveData
    }
}