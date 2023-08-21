package com.example.esewamarket.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.esewamarket.databinding.ItemProductsBinding
import com.example.esewamarket.models.ProductsItem

class AllProductsAdadpter : RecyclerView.Adapter<AllProductsAdadpter.ViewHolder>() {

    private var allProductsList = arrayListOf<ProductsItem>()
    var onProductItemClick : ((ProductsItem) -> Unit)? = null
    var onAddToCartClick : ((ProductsItem) -> Unit)?= null


    @SuppressLint("NotifyDataSetChanged")
    fun setAllProductsList(allProductsList: ArrayList<ProductsItem>) {
        this.allProductsList = allProductsList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding : ItemProductsBinding) : RecyclerView.ViewHolder(binding.root) {
        val addToCart = binding.addToCartCardView
    }


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
        return allProductsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(allProductsList[position].image)
            .into(holder.binding.productImage)

        holder.binding.productName.text = allProductsList[position].title
        holder.binding.productCategory.text = allProductsList[position].category
        holder.binding.productPrice.text = allProductsList[position].price.toString()

        holder.itemView.setOnClickListener{
            onProductItemClick?.invoke(allProductsList[position])
        }


        holder.addToCart.setOnClickListener {
            onAddToCartClick?.invoke(allProductsList[position])
        }

    }
}