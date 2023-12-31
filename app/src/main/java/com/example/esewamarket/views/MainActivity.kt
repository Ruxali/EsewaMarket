package com.example.esewamarket.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.example.esewamarket.CircleIndicatorForBanner
import com.example.esewamarket.utils.HorizontalSpacesItemDecoration
import com.example.esewamarket.R
import com.example.esewamarket.utils.VerticalSpaceItemDecoration
import com.example.esewamarket.adapters.AllProductsAdadpter
import com.example.esewamarket.adapters.BannerAdapter
import com.example.esewamarket.adapters.CategoriesAdapter
import com.example.esewamarket.adapters.FeaturedProductsAdapter
import com.example.esewamarket.adapters.HotDealsAdapter
import com.example.esewamarket.adapters.PopularBrandAdapter
import com.example.esewamarket.api.RetrofitInstance
import com.example.esewamarket.databinding.ActivityMainBinding
import com.example.esewamarket.repository.AllProductsRepository
import com.example.esewamarket.repository.CategoriesRepository
import com.example.esewamarket.models.Banner
import com.example.esewamarket.models.CartItems
import com.example.esewamarket.models.ProductsItem
import com.example.esewamarket.repository.CartRepository
import com.example.esewamarket.repository.FeaturedProductsRepository
import com.example.esewamarket.repository.HotDealsRepository
import com.example.esewamarket.repository.PopularBrandsRepository
import com.example.esewamarket.viewModelFactory.AllProductsViewModelFactory
import com.example.esewamarket.viewModelFactory.CategoriesViewModelFactory
import com.example.esewamarket.viewModelFactory.FeaturedProductsViewModelFactory
import com.example.esewamarket.viewModelFactory.HotDealsViewModelFactory
import com.example.esewamarket.viewModelFactory.PopularBrandViewModelFactory
import com.example.esewamarket.viewModelFactory.ProductDetailsViewModelFactory
import com.example.esewamarket.viewModels.AllProductsViewModel
import com.example.esewamarket.viewModels.CategoriesViewModel
import com.example.esewamarket.viewModels.FeaturedProductsViewModel
import com.example.esewamarket.viewModels.HotDealsViewModel
import com.example.esewamarket.viewModels.PopularBrandViewModel
import com.example.esewamarket.viewModels.ProductDetailsViewModel
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

    private lateinit var productDetailsViewModel: ProductDetailsViewModel

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //for categories
        prepareCategoriesRecyclerView()
        val categoriesRepository = CategoriesRepository(apiService)
        categoriesViewModel = ViewModelProvider(this, CategoriesViewModelFactory(categoriesRepository)).get(CategoriesViewModel::class.java)
        categoriesViewModel.categoriesLiveData.observe(this) { categoriesList ->
            categoriesAdapter.setCategoriesList(categoriesList)
        }

        //for featured products
        prepareFeaturedProductsRecyclerView()
        val featuredProductsRepository = FeaturedProductsRepository(apiService)
        featuredProductsViewModel = ViewModelProvider(this, FeaturedProductsViewModelFactory(featuredProductsRepository)).get(FeaturedProductsViewModel::class.java)
        featuredProductsViewModel.featuredProductsLiveData.observe(this) { featuredProductsList ->
            featuredProductsAdapter.setFeaturedProductsList(featuredProductsList)
        }

        //for hot deals
        prepareHotDealsRecyclerView()
        val hotDealsRepository = HotDealsRepository(apiService)
        hotDealsViewModel = ViewModelProvider(this, HotDealsViewModelFactory(hotDealsRepository)).get(HotDealsViewModel::class.java)
        hotDealsViewModel.hotDealsLiveData.observe(this) { hotDealsList ->
            hotDealsAdapter.setHotDealsList(hotDealsList)
        }

        //for popular brand
        preparePopularBrandRecyclerView()
        val popularBrandsRepository = PopularBrandsRepository(apiService)
        popularBrandViewModel = ViewModelProvider(this, PopularBrandViewModelFactory(popularBrandsRepository)).get(PopularBrandViewModel::class.java)
        popularBrandViewModel.popularBrandsLiveData.observe(this) { popularBrandsList ->
            popularBrandAdapter.setPopularBrandList(popularBrandsList)
        }


        //for all products
        prepareAllProductsRecyclerView()
        val allProductsRepository = AllProductsRepository(apiService)
        allProductsViewModel = ViewModelProvider(this, AllProductsViewModelFactory(allProductsRepository)).get(AllProductsViewModel::class.java)
        allProductsViewModel.allProductsLiveData.observe(this) { allProductsList ->
            allProductsAdapter.setAllProductsList(allProductsList)
        }

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
            addItemDecoration(HorizontalSpacesItemDecoration(resources.getDimensionPixelSize(R.dimen.space)))
            addItemDecoration(CircleIndicatorForBanner())

            //for snapping
            val snapHelper: SnapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(binding.bannerSection.rvBanner)
        }
        bannerAdapter.setBannerList(bannerList)

        //for bottom nav
        binding.bottomNavBar.bottomNavigation.setItemSelected(R.id.shop)
        binding.bottomNavBar.bottomNavigation.setOnItemSelectedListener{
            when(it){
                R.id.cart ->{
                    val intent = Intent(this@MainActivity, CartActivity::class.java)
                    startActivity(intent)
                }

                R.id.shop ->{
                    val intent = Intent(this@MainActivity, MainActivity::class.java)
                    startActivity(intent)
                }

            }
        }
        //for loading gif
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
        //to go to product details
        allProductsAdapter.onProductItemClick = {
            val intent = Intent(this@MainActivity, ProductsDetailActivity::class.java)
            intent.putExtra("productItem",it)
            startActivity(intent)
        }

        //add to cart
        allProductsAdapter.onAddToCartClick = {
            val cartRepository = CartRepository(this)
            val productDetailsViewModelFactory = ProductDetailsViewModelFactory(cartRepository)
            productDetailsViewModel = ViewModelProvider(this, productDetailsViewModelFactory).get(ProductDetailsViewModel::class.java)

            // Create a Product instance for the product you want to add
            val productToAdd = ProductsItem(it.category,it.description,it.id,it.image,it.price,it.title)
            // Add the product to the cart using the ViewModel
            productDetailsViewModel.addToCart(productToAdd)

            goToCartPage()
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
        //to go to product details
        popularBrandAdapter.onProductItemClick = {
            val intent = Intent(this@MainActivity, ProductsDetailActivity::class.java)
            intent.putExtra("productItem",it)
            startActivity(intent)
        }

        //add to cart
        popularBrandAdapter.onAddToCartClick = {
             val cartRepository = CartRepository(this)
            val productDetailsViewModelFactory = ProductDetailsViewModelFactory(cartRepository)
            productDetailsViewModel = ViewModelProvider(this, productDetailsViewModelFactory).get(ProductDetailsViewModel::class.java)

            // Create a Product instance for the product you want to add
            val productToAdd = ProductsItem(it.category,it.description,it.id,it.image,it.price,it.title)
            // Add the product to the cart using the ViewModel
            productDetailsViewModel.addToCart(productToAdd)

            goToCartPage()
        }
    }

    private fun prepareHotDealsRecyclerView() {
        hotDealsAdapter = HotDealsAdapter()
        binding.hotDealsSection.rvHotDeals.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = hotDealsAdapter
            addItemDecoration(HorizontalSpacesItemDecoration(resources.getDimensionPixelSize(R.dimen.space)))
        }
        //to go to product details
        hotDealsAdapter.onProductItemClick = {
            val intent = Intent(this@MainActivity, ProductsDetailActivity::class.java)
            intent.putExtra("productItem",it)
            startActivity(intent)
        }

        //add to cart
        hotDealsAdapter.onAddToCartClick = {
             val cartRepository = CartRepository(this)
            val productDetailsViewModelFactory = ProductDetailsViewModelFactory(cartRepository)
            productDetailsViewModel = ViewModelProvider(this, productDetailsViewModelFactory).get(ProductDetailsViewModel::class.java)

            // Create a Product instance for the product you want to add
            val productToAdd = ProductsItem(it.category,it.description,it.id,it.image,it.price,it.title)
            // Add the product to the cart using the ViewModel
            productDetailsViewModel.addToCart(productToAdd)

            goToCartPage()
        }
    }

    private fun prepareFeaturedProductsRecyclerView() {
        featuredProductsAdapter = FeaturedProductsAdapter()
        binding.featuredProductsSection.rvFeaturedProducts.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = featuredProductsAdapter
            addItemDecoration(HorizontalSpacesItemDecoration(resources.getDimensionPixelSize(R.dimen.space)))
        }

        //to go to product details
        featuredProductsAdapter.onProductItemClick = {
            val intent = Intent(this@MainActivity, ProductsDetailActivity::class.java)
            intent.putExtra("productItem",it)
            startActivity(intent)
        }

        //add to cart
        featuredProductsAdapter.onAddToCartClick = {
             val cartRepository = CartRepository(this)
            val productDetailsViewModelFactory = ProductDetailsViewModelFactory(cartRepository)
            productDetailsViewModel = ViewModelProvider(this, productDetailsViewModelFactory).get(ProductDetailsViewModel::class.java)

            // Create a Product instance for the product you want to add
            val productToAdd = ProductsItem(it.category,it.description,it.id,it.image,it.price,it.title)
            // Add the product to the cart using the ViewModel
            productDetailsViewModel.addToCart(productToAdd)

            goToCartPage()
        }
    }

    private fun goToCartPage() {
        val intent = Intent(this@MainActivity, CartActivity::class.java)
        startActivity(intent)
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