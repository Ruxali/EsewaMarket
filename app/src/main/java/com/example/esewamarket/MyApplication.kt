package com.example.esewamarket

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.esewamarket.models.Banner
import com.google.gson.Gson


class MyApplication: Application() {

    companion object {
                @get:Synchronized
                lateinit var initializer: MyApplication
                        private set

        }

        @SuppressLint("CommitPrefEdits")
        override fun onCreate() {
            super.onCreate()

            initializer=this


            //to save
            val sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val gson = Gson()
            editor.putString("bannerListData",gson.toJson(Banner.getDummyBanner()))
            editor.apply()

        }


}