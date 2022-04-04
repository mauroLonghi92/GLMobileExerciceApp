package com.example.glmobileexerciceapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entitie.ItemEntity
import com.example.glmobileexerciceapp.R
import com.example.glmobileexerciceapp.databinding.ViewItemListBinding
import com.example.glmobileexerciceapp.extension.getImageByUrl

interface OnItemClicked {
    fun onItemClicked(item: ItemEntity)
}

class ItemsAdapter(private val onItemClicked: OnItemClicked) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    private val items = mutableListOf<ItemEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_item_list,
                parent,
                false
            ),
            onItemClicked
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(itemList: List<ItemEntity>) {
        items.addAll(itemList)
    }

    class ViewHolder(itemView: View, private val onItemClicked: OnItemClicked) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = ViewItemListBinding.bind(itemView)
        fun bind(item: ItemEntity) = with(itemView) {
            itemView.setOnClickListener { onItemClicked.onItemClicked(item) }
            item.let {
                binding.apply {
                    this.itemName.text = it.title
                    this.itemDescription.text = it.description
                    this.itemImage.getImageByUrl(it.image)

                }
            }
        }
    }
}