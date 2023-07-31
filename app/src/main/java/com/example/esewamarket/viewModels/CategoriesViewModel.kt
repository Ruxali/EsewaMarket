package com.example.esewamarket.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.esewamarket.api.ApiService
import com.example.esewamarket.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesViewModel: ViewModel() {
    private var categoriesLiveData= MutableLiveData<List<String>>()
    private var retrofitInstance = RetrofitInstance

    fun getCategories(){
        retrofitInstance.apiInterface.getCategories().enqueue(object  :
            Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.body() != null){
                    categoriesLiveData.value = response.body()
                }
                else{
                    return
                }
            }
            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.d("TAG",t.message.toString())
            }
        })
    }
    fun observecategoriesLiveData() : MutableLiveData<List<String>> {
        return categoriesLiveData
    }

}