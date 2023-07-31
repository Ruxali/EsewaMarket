package com.example.esewamarket.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.esewamarket.databinding.ItemCategoriesBinding

class CategoriesAdapter: RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    private var categoriesList = listOf<String>()
    @SuppressLint("NotifyDataSetChanged")
    fun setCategoriesList(categoriesList: List<String>){
        this.categoriesList = categoriesList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding : ItemCategoriesBinding) : RecyclerView.ViewHolder(binding.root)  {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoriesBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
        holder.binding.categoriesName.text = categoriesList[position]
    }
}