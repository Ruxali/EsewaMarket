package com.example.esewamarket.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esewamarket.api.RetrofitInstance
import com.example.esewamarket.repository.CategoriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CategoriesViewModel(val categoryRepository: CategoriesRepository): ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO ){
            categoryRepository.getCategories()
        }
    }

    val categories: LiveData<List<String>>
        get() = categoryRepository.categories
//    fun getCategories(){
//        retrofitInstance.apiInterface.getCategories().enqueue(object  :
//            Callback<List<String>> {
//            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
//                if (response.body() != null){
//                    categoriesLiveData.value = response.body()
//                }
//                else{
//                    return
//                }
//            }
//            override fun onFailure(call: Call<List<String>>, t: Throwable) {
//                Log.d("TAG",t.message.toString())
//            }
//        })
//    }
//    fun observecategoriesLiveData() : MutableLiveData<List<String>> {
//        return categoriesLiveData
//    }

}