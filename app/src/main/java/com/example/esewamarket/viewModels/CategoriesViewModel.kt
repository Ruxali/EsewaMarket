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


class CategoriesViewModel(private val categoryRepository: CategoriesRepository): ViewModel() {

    val categoriesLiveData by lazy { MutableLiveData<List<String>>() }


    init {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesLiveData.postValue(categoryRepository.getCategories())
        }
    }

}