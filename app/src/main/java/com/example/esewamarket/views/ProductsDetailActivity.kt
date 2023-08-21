package com.example.esewamarket.views

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.esewamarket.adapters.CartItemAdapter
import com.example.esewamarket.databinding.ActivityProductsDetailBinding
import com.example.esewamarket.models.CartItems
import com.example.esewamarket.models.ProductsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ProductsDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductsDetailBinding

    @SuppressLint("CommitPrefEdits", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //for back
        binding.productDescriptionImage.productToolbar.backButton.setOnClickListener {
            val intent = Intent(this@ProductsDetailActivity, MainActivity::class.java)
            startActivity(intent)
        }

        //for product details fetch
        val products = intent.getParcelableExtra<ProductsItem>("productItem")
        if(products != null){
            Glide.with(this).load(products.image)
                .into(binding.productDescriptionImage.productImage)
            binding.productDescription.productName.text = products.title
            binding.productDescription.productPrice.text = products.price.toString()
            binding.productDescription.productDescription.text = products.description
            binding.productDescription.productCategory.text = products.category

            //for view more in description
            binding.productDescription.productDescription.setInterpolator(OvershootInterpolator())

//            val lineCount = binding.productDescription.productDescription.lineCount
//
//            if(lineCount < 4){
//                binding.productDescription.viewMoreButton.visibility = View.GONE
//            }else{
//                binding.productDescription.viewMoreButton.visibility = View.VISIBLE
//            }

            binding.productDescription.viewMoreButton.setOnClickListener {
                if (binding.productDescription.productDescription.isExpanded)
                {
                    binding.productDescription.viewMoreButton.text = "VIEW MORE"
                    binding.productDescription.productDescription.collapse()
                }
                else
                {
                    binding.productDescription.viewMoreButton.text = "VIEW LESS"
                    binding.productDescription.productDescription.expand()
                }
            }

            //for bottom nav
            binding.productBottomNav.productName.text = products.title
            binding.productBottomNav.productPrice.text = products.price.toString()
        }

        //for add to cart
        val sharedPreferences= getSharedPreferences("shopping_cart", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()

        binding.productBottomNav.addToCartBtn.setOnClickListener {
             val jsonLoad: String? = sharedPreferences.getString("cartList",null)
             val type = object : TypeToken<List<CartItems>>() {}.type
             val cartListOutput = gson.fromJson<MutableList<CartItems>>(jsonLoad,type)?: mutableListOf()

            if (products != null) {
                var isItemAlreadyPresent = false
                for(item in cartListOutput){

                    if(item.id == products.id){
                        isItemAlreadyPresent = true
                        //increase quantity and price
                        item.quantity++

                        item.changedPrice = item.price * item.quantity

                        val toast = Toast.makeText(this@ProductsDetailActivity, "Already in the Cart, Quantity Increased", Toast.LENGTH_LONG)
                        toast.show()
                    }
                    else{
                        continue
                    }

                }
                if (!isItemAlreadyPresent) {
                    cartListOutput.add(CartItems(products.category,products.id,products.image,products.price,products.price,0.0,products.title, 1))
                }
            }
            val json: String = gson.toJson(cartListOutput)
            editor.putString("cartList",json)
            editor.apply()


            val toast = Toast.makeText(this@ProductsDetailActivity, "Successfully added to the Cart", Toast.LENGTH_LONG)
            toast.show()


         }

        //intent to cart
        binding.productDescriptionImage.productToolbar.cartIcon.setOnClickListener {
            val intent = Intent(this@ProductsDetailActivity, CartActivity::class.java)
            startActivity(intent)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@ProductsDetailActivity, MainActivity::class.java)
        startActivity(intent)
    }

}