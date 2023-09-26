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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.esewamarket.R
import com.example.esewamarket.adapters.CartItemAdapter
import com.example.esewamarket.databinding.ActivityCartBinding
import com.example.esewamarket.helper.CartItemSwipeController
import com.example.esewamarket.helper.CartSwipeHelper
import com.example.esewamarket.models.CartItems
import com.example.esewamarket.repository.CartRepository
import com.example.esewamarket.viewModels.CartViewModel
import com.example.esewamarket.viewModelFactory.CartViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import okhttp3.internal.notify


class CartActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCartBinding

    private lateinit var cartAdapter : CartItemAdapter

    private var cartSwipeHelper= CartSwipeHelper()

    private lateinit var itemTouchHelper : ItemTouchHelper

    private lateinit var cartViewModel: CartViewModel

    private var cartListOutput= listOf<CartItems>()

    private val gsonLoad = Gson()

    @SuppressLint("NotifyDataSetChanged", "InflateParams", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //for back
        binding.cartTopNav.backBtn.setOnClickListener{
            goToMain()
        }


        //for cart items
        val cartRepository = CartRepository(this)
        val cartListOutput = cartRepository.getCartItems()
        cartViewModel = ViewModelProvider(this, CartViewModelFactory(cartRepository)).get(CartViewModel::class.java)

        cartAdapter = CartItemAdapter()
        binding.cartItem.rvCartItems.apply {
            layoutManager = LinearLayoutManager(this@CartActivity, RecyclerView.VERTICAL,false)
            adapter = cartAdapter
        }

        cartViewModel.cartLiveData.observe(this) { cartList ->
            if (cartListOutput.isNotEmpty()){
                cartAdapter.setCartList(cartList)
                binding.cartItem.emptyCart.root.visibility = View.INVISIBLE
                binding.cartItem.rvCartItems.isVisible
            }else{
                binding.cartItem.rvCartItems.isInvisible
                binding.cartItem.emptyCart.root.visibility = View.VISIBLE

                //for continue shopping
                binding.cartItem.emptyCart.continueShopping.setOnClickListener {
                    goToMain()
                }
            }
        }

        cartViewModel.getDataFromProductDetails()

        //for cart count
        binding.cartItem.cartItemCount.text = cartListOutput.size.toString()
        binding.cartTopNav.cartValue.text = cartListOutput.size.toString()

        //for total price
        var totalPrice = 0.0
        for (element in cartRepository.getCartItems()) {
            totalPrice += element.changedPrice
        }
        binding.cartBottomNav.totalPrice.text = totalPrice.toString()

        //for cart delete
        removeFomCart()

        // for increment in cart
        cartAdapter.onItemIncrease = {
            for(item in cartRepository.getCartItems()) {
                cartViewModel.increaseQuantity(item.id)
                cartAdapter.notifyDataSetChanged()
                startActivity(intent)
            }
        }

        // for decrement in cart
        cartAdapter.onItemDecrease = {
            for(item in cartRepository.getCartItems()) {
                    cartViewModel.decreaseQuantity(item.id)
                    cartAdapter.notifyDataSetChanged()
                    startActivity(intent)
            }
        }

    }

    private fun removeFomCart() {
        cartSwipeHelper.cartSwipeHelperFunction(object : CartItemSwipeController(){
            @SuppressLint("InflateParams")
            override fun onRightClicked(position: Int) {
                val view : View = layoutInflater.inflate(R.layout.activity_delete_from_cart_confirmation,null)
                val dialog = BottomSheetDialog(this@CartActivity)

                //for buttons
                val deleteBtn = view.findViewById<Button>(R.id.deleteBtn)
                val cancelBtn = view.findViewById<Button>(R.id.cancelBtn)

                deleteBtn.setOnClickListener {
                    cartViewModel.removeFromCart(position)
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

        binding.cartItem.rvCartItems.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                cartSwipeHelper.onDraw(c)
            }
        })
    }


    private fun goToMain() {
        val intent = Intent(this@CartActivity, MainActivity::class.java)
        startActivity(intent)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@CartActivity, MainActivity::class.java)
        startActivity(intent)
    }

}