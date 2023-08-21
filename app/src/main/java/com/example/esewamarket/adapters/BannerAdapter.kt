package com.example.esewamarket.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.esewamarket.R
import com.example.esewamarket.databinding.ItemBannerBinding
import com.example.esewamarket.models.Banner

class BannerAdapter : RecyclerView.Adapter<BannerAdapter.ViewHolder>() {

    private var bannerList = arrayListOf<Banner>()
    @SuppressLint("NotifyDataSetChanged")
    fun setBannerList(bannerList: ArrayList<Banner>) {
        this.bannerList = bannerList
        notifyDataSetChanged()
    }

    class ViewHolder( val binding : ItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBannerBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun getItemCount(): Int {
        return bannerList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(bannerList[position].imageUrl)
            .placeholder(R.drawable.bmp_ad_banner)
            .error(R.drawable.bmp_banner1)
            .into(holder.binding.bannerImage)
    }
}