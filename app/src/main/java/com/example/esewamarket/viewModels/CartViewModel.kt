package com.example.esewamarket.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esewamarket.adapters.CartItemAdapter
import com.example.esewamarket.models.CartItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(private val cartItemAdapter: CartItemAdapter):ViewModel() {

}