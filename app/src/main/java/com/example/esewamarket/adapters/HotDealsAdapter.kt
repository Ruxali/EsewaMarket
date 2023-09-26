package com.example.esewamarket.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.esewamarket.databinding.ItemHotDealsBinding
import com.example.esewamarket.models.ProductsItem

class HotDealsAdapter : RecyclerView.Adapter<HotDealsAdapter.ViewHolder>(){
    private var hotDealsList = arrayListOf<ProductsItem>()
    var onProductItemClick : ((ProductsItem) -> Unit)? = null

    var onAddToCartClick : ((ProductsItem) -> Unit)?= null

    @SuppressLint("NotifyDataSetChanged")
    fun setHotDealsList(hotDealsList: ArrayList<ProductsItem>) {
        this.hotDealsList = hotDealsList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding : ItemHotDealsBinding) : RecyclerView.ViewHolder(binding.root) {
        val addToCart = binding.addToCartCardView
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHotDealsBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun getItemCount(): Int {
        return hotDealsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(hotDealsList[position].image)
            .into(holder.binding.productImage)

        holder.binding.productName.text = hotDealsList[position].title
        holder.binding.productCategory.text = hotDealsList[position].category
        holder.binding.productPrice.text = hotDealsList[position].price.toString()

        holder.itemView.setOnClickListener{
            onProductItemClick?.invoke(hotDealsList[position])
        }


        holder.addToCart.setOnClickListener {
            onAddToCartClick?.invoke(hotDealsList[position])
        }
    }
}