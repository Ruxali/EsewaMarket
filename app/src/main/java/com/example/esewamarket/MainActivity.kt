package com.example.esewamarket

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.esewamarket.adapters.AllProductsAdadpter
import com.example.esewamarket.adapters.BannerAdapter
import com.example.esewamarket.adapters.CategoriesAdapter
import com.example.esewamarket.adapters.FeaturedProductsAdapter
import com.example.esewamarket.adapters.HotDealsAdapter
import com.example.esewamarket.adapters.PopularBrandAdapter
import com.example.esewamarket.api.ApiService
import com.example.esewamarket.api.RetrofitInstance
import com.example.esewamarket.databinding.ActivityMainBinding
import com.example.esewamarket.models.Banner
import com.example.esewamarket.repository.CategoriesRepository
import com.example.esewamarket.viewModelFactory.CategoriesViewModelFactory
import com.example.esewamarket.viewModels.AllProductsViewModel
import com.example.esewamarket.viewModels.CategoriesViewModel
import com.example.esewamarket.viewModels.FeaturedProductsViewModel
import com.example.esewamarket.viewModels.HotDealsViewModel
import com.example.esewamarket.viewModels.PopularBrandViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val apiService = RetrofitInstance.apiInterface


    //for categories
    private lateinit var categoriesViewModel: CategoriesViewModel
    private lateinit var categoriesAdapter : CategoriesAdapter

    private lateinit var featuredProductsViewModel: FeaturedProductsViewModel
    private lateinit var featuredProductsAdapter : FeaturedProductsAdapter

    private lateinit var hotDealsViewModel: HotDealsViewModel
    private lateinit var hotDealsAdapter : HotDealsAdapter

    private lateinit var popularBrandViewModel: PopularBrandViewModel
    private lateinit var popularBrandAdapter : PopularBrandAdapter

    private lateinit var allProductsViewModel: AllProductsViewModel
    private lateinit var allProductsAdapter : AllProductsAdadpter

    private lateinit var bannerAdapter : BannerAdapter


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //for categories
        prepareCategoriesRecyclerView()

        val categoriesRepository = CategoriesRepository(apiService)
        categoriesViewModel = ViewModelProvider(this, CategoriesViewModelFactory(categoriesRepository)).get(CategoriesViewModel::class.java)
        categoriesViewModel.categories.observe(this, {categoriesList ->
            categoriesAdapter.setCategoriesList(categoriesList)
        })

        //for featured products
        prepareHotDealsRecyclerView()

        hotDealsViewModel = ViewModelProvider(this)[HotDealsViewModel::class.java]
        hotDealsViewModel.getHotDeals()
        hotDealsViewModel.observeHotDealsLiveData().observe(this, Observer { hotDealsList ->
            hotDealsAdapter.setHotDealsList(hotDealsList)

        })

        //for hot deals
        prepareFeaturedProductsRecyclerView()

        featuredProductsViewModel = ViewModelProvider(this)[FeaturedProductsViewModel::class.java]
        featuredProductsViewModel.getFeaturedProducts()
        featuredProductsViewModel.observeFeaturedProductsLiveData().observe(this, Observer { featuredProductsList ->
            featuredProductsAdapter.setFeaturedProductsList(featuredProductsList)
        })

        //for popular brand
        preparePopularBrandRecyclerView()

        popularBrandViewModel = ViewModelProvider(this)[PopularBrandViewModel::class.java]
        popularBrandViewModel.getPopularBrand()
        popularBrandViewModel.observePopularBrandLiveData().observe(this, Observer { popularBrandList ->
            popularBrandAdapter.setPopularBrandList(popularBrandList)
        })

        //for all products
        prepareAllProductsRecyclerView()

        allProductsViewModel = ViewModelProvider(this)[AllProductsViewModel::class.java]
        allProductsViewModel.getAllProducts()
        allProductsViewModel.observeAllProductsLiveData().observe(this, Observer { allProductsList ->
            allProductsAdapter.setAllProductsList(allProductsList)
        })

        //recycler view of image banner
        val sharedPreferencesLoad = getSharedPreferences("sharedPreferences", MODE_PRIVATE)
        val gsonLoad = Gson()
        val jsonLoad: String? = sharedPreferencesLoad.getString("bannerListData",null)
        val type = object : TypeToken<ArrayList<Banner>>() {}.type

       val  bannerList = gsonLoad.fromJson<ArrayList<Banner>>(jsonLoad,type)
            bannerAdapter = BannerAdapter()
            binding.bannerSection.rvBanner.apply {
                layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL,false)
                adapter = bannerAdapter
            }
        bannerAdapter.setBannerList(bannerList)

        //for bottom nav
        binding.bottomNavBar.bottomNavigation.setItemSelected(R.id.shop)

        //for loadin gif
        Glide.with(this).load(R.drawable.load).into(binding.loadView.webView)

    }

    private fun prepareAllProductsRecyclerView() {
        allProductsAdapter = AllProductsAdadpter()
        binding.allProductsSection.rvAllProducts.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = allProductsAdapter
            addItemDecoration(HorizontalSpacesItemDecoration(resources.getDimensionPixelSize(R.dimen.space)))
            addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.space)))
        }
    }

    private fun preparePopularBrandRecyclerView() {
        popularBrandAdapter = PopularBrandAdapter()
        binding.popularBrandSection.rvPopularSection.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = popularBrandAdapter
            addItemDecoration(HorizontalSpacesItemDecoration(resources.getDimensionPixelSize(R.dimen.space)))
            addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.space)))
        }
    }

    private fun prepareHotDealsRecyclerView() {
        hotDealsAdapter = HotDealsAdapter()
        binding.hotDealsSection.rvHotDeals.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = hotDealsAdapter
            addItemDecoration(HorizontalSpacesItemDecoration(resources.getDimensionPixelSize(R.dimen.space)))
        }
    }

    private fun prepareFeaturedProductsRecyclerView() {
        featuredProductsAdapter = FeaturedProductsAdapter()
        binding.featuredProductsSection.rvFeaturedProducts.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = featuredProductsAdapter
            addItemDecoration(HorizontalSpacesItemDecoration(resources.getDimensionPixelSize(R.dimen.space)))
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.categorySection.rvCategories.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL,false)
            adapter = categoriesAdapter
            addItemDecoration(HorizontalSpacesItemDecoration(resources.getDimensionPixelSize(R.dimen.space)))
        }
    }

}