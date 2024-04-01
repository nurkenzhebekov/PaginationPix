package com.example.paginationpix

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.paginationpix.databinding.ItemImageBinding

class PixaAdapter(var list: ArrayList<ImageModel>) : Adapter<PixaAdapter.PixaViewHolder>() {
    class PixaViewHolder(var binding: ItemImageBinding) : ViewHolder(binding.root){
        fun bind(model: ImageModel) {
            binding.imgvItemImage.load(model.largeImageURL)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PixaViewHolder {
        return PixaViewHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    fun addList(listImage : ArrayList<ImageModel>) {
        list.addAll(listImage)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PixaViewHolder, position: Int) {
        holder.bind(list[position])
    }
}