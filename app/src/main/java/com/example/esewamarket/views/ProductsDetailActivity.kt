package com.example.esewamarket.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.esewamarket.databinding.ActivityProductsDetailBinding
import com.example.esewamarket.models.ProductsItem
import com.example.esewamarket.repository.CartRepository
import com.example.esewamarket.viewModels.CartViewModel
import com.example.esewamarket.viewModelFactory.CartViewModelFactory
import com.example.esewamarket.viewModelFactory.ProductDetailsViewModelFactory
import com.example.esewamarket.viewModels.ProductDetailsViewModel


class ProductsDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductsDetailBinding
    private lateinit var productDetailsViewModel: ProductDetailsViewModel

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
            binding.productDescription.productId.text = products.id.toString()

            //for view more in description
            binding.productDescription.productDescription.setInterpolator(OvershootInterpolator())

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
        val sharedPreferences= getSharedPreferences("shopping_cart", MODE_PRIVATE).edit()
        val cartRepository = CartRepository(this)
        val productDetailsViewModelFactory = ProductDetailsViewModelFactory(cartRepository)
        productDetailsViewModel = ViewModelProvider(this, productDetailsViewModelFactory).get(ProductDetailsViewModel::class.java)

        binding.productBottomNav.addToCartBtn.setOnClickListener {

            // Create a Product instance for the product you want to add
            val productToAdd = products?.let { it1 -> ProductsItem(it1.category,it1.description,it1.id,it1.image,it1.price,it1.title) }
            // Add the product to the cart using the ViewModel
            if (productToAdd != null) {
                productDetailsViewModel.addToCart(productToAdd)
            }

            goToCartPage()
         }

        //intent to cart
        binding.productDescriptionImage.productToolbar.cartIcon.setOnClickListener {
            goToCartPage()
        }
    }

    private fun goToCartPage() {
        val intent = Intent(this@ProductsDetailActivity, CartActivity::class.java)
        startActivity(intent)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@ProductsDetailActivity, MainActivity::class.java)
        startActivity(intent)
    }



}
