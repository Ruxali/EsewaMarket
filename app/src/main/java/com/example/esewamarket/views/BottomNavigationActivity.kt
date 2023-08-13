package com.example.esewamarket.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.esewamarket.R
import com.example.esewamarket.databinding.ActivityBottomNavigationBinding

class BottomNavigationActivity : AppCompatActivity() {

    lateinit var binding: ActivityBottomNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.setItemSelected(R.id.shop)

//        binding.bottomNavigation.setOnItemSelectedListener {
//            when(it) {
//                R.id.shop -> {
//                    binding.bottomNavigation.showBadge(R.id.cart)
//                }
//                R.id.cart -> {
//                    binding.bottomNavigation.showBadge(R.id.shop)
//                }
//            }
//        }
    }
}