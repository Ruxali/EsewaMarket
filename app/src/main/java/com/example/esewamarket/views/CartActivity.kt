package com.example.esewamarket.views

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.esewamarket.R
import com.example.esewamarket.adapters.CartItemAdapter
import com.example.esewamarket.databinding.ActivityCartBinding
import com.example.esewamarket.helper.CartItemSwipeController
import com.example.esewamarket.helper.CartSwipeHelper
import com.example.esewamarket.models.CartItems
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CartActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCartBinding

    private lateinit var cartAdapter : CartItemAdapter

    private var cartSwipeHelper= CartSwipeHelper()

    private lateinit var itemTouchHelper : ItemTouchHelper

     private var cartListOutput= mutableListOf <CartItems>()
     val gsonLoad = Gson()

    @SuppressLint("NotifyDataSetChanged", "InflateParams", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //for back
        binding.cartTopNav.backBtn.setOnClickListener{
            val intent = Intent(this@CartActivity, MainActivity::class.java)
            startActivity(intent)
        }

        cartAdapter = CartItemAdapter()
        binding.cartItem.rvCartItems.apply {
            layoutManager = LinearLayoutManager(this@CartActivity, RecyclerView.VERTICAL,false)
            adapter = cartAdapter
        }


        //for cart items
        val sharedPref= getSharedPreferences("shopping_cart", MODE_PRIVATE)
        val editor = sharedPref.edit()
        val jsonLoad: String? = sharedPref.getString("cartList",null)
        val type = object : TypeToken<MutableList<CartItems>>() {}.type

        cartListOutput = gsonLoad.fromJson(jsonLoad,type) ?: mutableListOf()
        if(cartListOutput.isNotEmpty()){
            cartAdapter.setCartList(cartListOutput)

            binding.cartItem.emptyCart.root.visibility = View.INVISIBLE
            binding.cartItem.rvCartItems.isVisible
        }else{
            binding.cartItem.rvCartItems.isInvisible
            binding.cartItem.emptyCart.root.visibility = View.VISIBLE

            //for continue shopping
            binding.cartItem.emptyCart.continueShopping.setOnClickListener {
                val intent = Intent(this@CartActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }

        //for total price
        var totalPrice = 0.0
        for (i in 0 until cartListOutput.size) {
            totalPrice += cartListOutput[i].changedPrice
        }
        binding.cartBottomNav.totalPrice.text = totalPrice.toString()

        //for cart count
        binding.cartItem.cartItemCount.text = cartListOutput.size.toString()
        binding.cartTopNav.cartValue.text = cartListOutput.size.toString()

        //for cart delete
        cartSwipeHelper.cartSwipeHelperFunction(object : CartItemSwipeController(){
            @SuppressLint("InflateParams")
            override fun onRightClicked(position: Int) {
                val view : View = layoutInflater.inflate(R.layout.activity_delete_from_cart_confirmation,null)
                val dialog = BottomSheetDialog(this@CartActivity)

                //for buttons
                val deleteBtn = view.findViewById<Button>(R.id.deleteBtn)
                val cancelBtn = view.findViewById<Button>(R.id.cancelBtn)

                deleteBtn.setOnClickListener {
                    cartListOutput.removeAt(position)

                    val json: String = gsonLoad.toJson(cartListOutput)
                    editor.putString("cartList",json)
                    editor.apply()

                    cartAdapter.setCartList(cartListOutput)
                    cartAdapter.notifyItemRemoved(position)
                    startActivity(intent)

                    dialog.dismiss()

                }

                cancelBtn.setOnClickListener {
                    dialog.dismiss()
                }

                dialog.setCancelable(false)
                dialog.setContentView(view)
                dialog.show()
            }
        })

        //for delete swipe
        itemTouchHelper = ItemTouchHelper(cartSwipeHelper)
        itemTouchHelper.attachToRecyclerView(binding.cartItem.rvCartItems)

        binding.cartItem.rvCartItems.addItemDecoration(object : ItemDecoration() {
            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                cartSwipeHelper.onDraw(c)
            }
        })

        // for increment in cart
        cartAdapter.onItemIncrease = {

            for(item in cartListOutput) {

                if (item.id == it.id) {
                    //increase quantity and price
                    item.quantity++
                    //change in price accordingly
                    item.changedPrice = item.price * item.quantity

                    totalPrice += item.price
                    binding.cartBottomNav.totalPrice.text = totalPrice.toString()
                    cartAdapter.notifyDataSetChanged()
                }
            }
           updateSharedPreference()
        }


        // for decrement in cart
        cartAdapter.onItemDecrease = {cartItems ->

            for(item in cartListOutput) {

                if (item.id == cartItems.id) {
                    val position = cartListOutput.indexOf(item)
                        //decrease quantity and price
                        cartItems.quantity--
                        //change in price accordingly
                        cartItems.changedPrice = cartItems.price * cartItems.quantity

                        totalPrice -= item.price
                        binding.cartBottomNav.totalPrice.text = totalPrice.toString()
                        cartAdapter.notifyDataSetChanged()

                    updateSharedPreference()

                    // if quantity is less than 1
                    if(cartItems.quantity < 1) {

                        totalPrice += cartItems.price
                        binding.cartBottomNav.totalPrice.text = totalPrice.toString()

                        val view: View = layoutInflater.inflate(
                            R.layout.activity_delete_from_cart_confirmation,
                            null
                        )
                        val dialog = BottomSheetDialog(this@CartActivity)

                        //for buttons
                        val deleteBtn = view.findViewById<Button>(R.id.deleteBtn)
                        val cancelBtn = view.findViewById<Button>(R.id.cancelBtn)

                        deleteBtn.setOnClickListener {
                            cartListOutput.removeAt(position)

                            updateSharedPreference()

                            cartAdapter.setCartList(cartListOutput)
                            cartAdapter.notifyItemRemoved(position)
                            startActivity(intent)

                            dialog.dismiss()

                        }

                        cancelBtn.setOnClickListener {
                            cartItems.quantity = 1
                            cartItems.changedPrice = cartItems.price
                            cartAdapter.notifyDataSetChanged()

                           updateSharedPreference()

                            dialog.dismiss()
                        }

                        dialog.setCancelable(false)
                        dialog.setContentView(view)
                        dialog.show()


                    }
                }
            }
        }



    }

    private fun updateSharedPreference() {
        val sharedPref= getSharedPreferences("shopping_cart", MODE_PRIVATE)
        val editor = sharedPref.edit()
        val json: String = gsonLoad.toJson(cartListOutput)
        editor.putString("cartList",json)
        editor.apply()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@CartActivity, MainActivity::class.java)
        startActivity(intent)
    }

}