package com.example.esewamarket.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.esewamarket.databinding.ItemProductsBinding
import com.example.esewamarket.models.CartItems
import com.example.esewamarket.models.ProductsItem
import com.google.gson.reflect.TypeToken

class FeaturedProductsAdapter: RecyclerView.Adapter<FeaturedProductsAdapter.ViewHolder>() {

    private var featuredProductsList = arrayListOf<ProductsItem>()

    var onProductItemClick : ((ProductsItem) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setFeaturedProductsList(featuredProductsList: ArrayList<ProductsItem>) {
        this.featuredProductsList = featuredProductsList
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
        return featuredProductsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(featuredProductsList[position].image)
            .into(holder.binding.productImage)

        holder.binding.productName.text = featuredProductsList[position].title
        holder.binding.productCategory.text = featuredProductsList[position].category
        holder.binding.productPrice.text = featuredProductsList[position].price.toString()


        holder.itemView.setOnClickListener{
            onProductItemClick?.invoke(featuredProductsList[position])
        }


    }
}