package com.example.esewamarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.esewamarket.adapters.CategoriesAdapter
import com.example.esewamarket.adapters.FeaturedProductsAdapter
import com.example.esewamarket.adapters.HotDealsAdapter
import com.example.esewamarket.adapters.PopularBrandAdapter
import com.example.esewamarket.databinding.ActivityMainBinding
import com.example.esewamarket.viewModels.CategoriesViewModel
import com.example.esewamarket.viewModels.FeaturedProductsViewModel
import com.example.esewamarket.viewModels.HotDealsViewModel
import com.example.esewamarket.viewModels.PopularBrandViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var categoriesViewModel: CategoriesViewModel
    private lateinit var categoriesAdapter : CategoriesAdapter

    private lateinit var featuredProductsViewModel: FeaturedProductsViewModel
    private lateinit var featuredProductsAdapter : FeaturedProductsAdapter

    private lateinit var hotDealsViewModel: HotDealsViewModel
    private lateinit var hotDealsAdapter : HotDealsAdapter

    private lateinit var popularBrandViewModel: PopularBrandViewModel
    private lateinit var popularBrandAdapter : PopularBrandAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //for categories
        prepareCategoriesRecyclerView()

        categoriesViewModel = ViewModelProvider(this)[CategoriesViewModel::class.java]
        categoriesViewModel.getCategories()
        categoriesViewModel.observecategoriesLiveData().observe(this, Observer { categoriesList ->
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