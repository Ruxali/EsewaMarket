package com.example.esewamarket.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.esewamarket.api.RetrofitInstance
import com.example.esewamarket.models.ProductsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotDealsViewModel : ViewModel() {
    private var hotDealsLiveData = MutableLiveData<ArrayList<ProductsItem>>()
    private var retrofitInstance = RetrofitInstance

    fun getHotDeals(){
        retrofitInstance.apiInterface.getHotDeals("2","desc").enqueue(object :
            Callback<ArrayList<ProductsItem>> {
            override fun onResponse(
                call: Call<ArrayList<ProductsItem>>,
                response: Response<ArrayList<ProductsItem>>
            ) {
                if (response.body() != null){
                    hotDealsLiveData.value = response.body()
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
    fun observeHotDealsLiveData() : MutableLiveData<ArrayList<ProductsItem>> {
        return hotDealsLiveData
    }
}