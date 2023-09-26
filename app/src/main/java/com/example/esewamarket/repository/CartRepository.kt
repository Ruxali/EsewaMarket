package com.example.esewamarket.repository

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat.getParcelableExtra
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.esewamarket.adapters.CartItemAdapter
import com.example.esewamarket.interfaces.CartDataSources
import com.example.esewamarket.models.CartItems
import com.example.esewamarket.models.ProductsItem
import com.example.esewamarket.views.MainActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartRepository(private val context: Context): CartDataSources {

    private val sharedPreferences : SharedPreferences= context.getSharedPreferences("shopping_cart", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    private val gson = Gson()
    private val gsonLoad = Gson()


    //show cart products
    override fun getCartItems(): List<CartItems> {
        val cartJsonLoad = sharedPreferences.getString("cartList",null)
        return if(cartJsonLoad != null){
            val cartType = object : TypeToken<List<CartItems>>() {}.type
            gsonLoad.fromJson(cartJsonLoad,cartType)
        } else{
            emptyList()
        }
    }

    //add to cart from button
    override fun addToCart(products: ProductsItem,quantity: Int) {
        val cartItems = getCartItems().toMutableList()
        val existingItem = cartItems.find { it.id == products.id }

        if (existingItem != null) {
            existingItem.quantity ++
            existingItem.changedPrice = existingItem.price * existingItem.quantity
        } else {
            cartItems.add(CartItems(products.category, products.id, products.image,products.price,products.price, 0.0,products.title, quantity))
        }

        val cartJson = gson.toJson(cartItems)
        sharedPreferences.edit().putString("cartList", cartJson).apply()
    }

    //for remove from cart
    override fun removeFromCart(id: Int) {
        val cartItems = getCartItems().toMutableList()
        val existingItem = cartItems.find { it.id == id}

        if (existingItem != null) {
            cartItems.remove(existingItem)
        }

        val cartJson = gson.toJson(cartItems)
        sharedPreferences.edit().putString("cartList", cartJson).apply()
    }

    //for increase in quantity
    override fun increaseQuantity(id: Int) {
        val cartItems = getCartItems().toMutableList()
        val existingItem = cartItems.find { it.id == id}

        if (existingItem != null) {
            existingItem.quantity ++
            existingItem.changedPrice = existingItem.price * existingItem.quantity
        }

        val cartJson = gson.toJson(cartItems)
        sharedPreferences.edit().putString("cartList", cartJson).apply()
    }

    //for decrease in quantity
    override fun decreaseQuantity(id: Int) {
        val cartItems = getCartItems().toMutableList()
        val existingItem = cartItems.find { it.id == id}

        if (existingItem != null) {
            existingItem.quantity --
            existingItem.changedPrice = existingItem.price * existingItem.quantity
        }

        val cartJson = gson.toJson(cartItems)
        sharedPreferences.edit().putString("cartList", cartJson).apply()
    }

}