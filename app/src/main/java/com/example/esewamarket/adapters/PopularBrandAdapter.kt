package com.example.esewamarket.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.esewamarket.databinding.ItemProductsBinding
import com.example.esewamarket.models.ProductsItem

class PopularBrandAdapter: RecyclerView.Adapter<PopularBrandAdapter.ViewHolder>() {

    private var popularBrandList = arrayListOf<ProductsItem>()
    @SuppressLint("NotifyDataSetChanged")
    fun setPopularBrandList(popularBrandList: ArrayList<ProductsItem>) {
        this.popularBrandList = popularBrandList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding : ItemProductsBinding) : RecyclerView.ViewHolder(binding.root) {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductsBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun getItemCount(): Int {
        return popularBrandList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(popularBrandList[position].image)
            .into(holder.binding.productImage)

        holder.binding.productName.text = popularBrandList[position].title
        holder.binding.productCategory.text = popularBrandList[position].category
        holder.binding.productPrice.text = popularBrandList[position].price.toString()

    }
}