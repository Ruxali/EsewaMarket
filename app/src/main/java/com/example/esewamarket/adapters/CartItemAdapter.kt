package com.example.esewamarket.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.esewamarket.databinding.ItemCartItemsBinding
import com.example.esewamarket.models.CartItems
import com.example.esewamarket.viewModels.CartViewModel

class CartItemAdapter :
        RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {

    private var cartList = listOf<CartItems>()
    var onItemIncrease : ((CartItems) -> Unit)?= null
    var onItemDecrease : ((CartItems) -> Unit)?= null

    @SuppressLint("NotifyDataSetChanged")
    fun setCartList(cartList: List<CartItems>) {
        this.cartList = cartList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding : ItemCartItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        val plusProduct = binding.plusProduct
        val minusProduct = binding.minusProduct
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCartItemsBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(cartList[position].image)
            .into(holder.binding.productImageCart)

        holder.binding.productName.text = cartList[position].title
        holder.binding.productCategory.text = cartList[position].category
        holder.binding.productPrice.text = cartList[position].changedPrice.toString()
        holder.binding.quantity.text = cartList[position].quantity.toString()
        holder.binding.productId.text = cartList[position].id.toString()

        holder.plusProduct.setOnClickListener {
            onItemIncrease?.invoke(cartList[position])
        }

        holder.minusProduct.setOnClickListener {
            onItemDecrease?.invoke(cartList[holder.adapterPosition])
        }

    }
}