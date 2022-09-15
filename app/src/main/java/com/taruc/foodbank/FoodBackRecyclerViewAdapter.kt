package com.taruc.foodbank

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.taruc.foodbank.databinding.FragmentItemBinding
import com.taruc.foodbank.entity.foodBank

/**
 * [RecyclerView.Adapter] that can display a [foodBank].
 * TODO: Replace the implementation with code for your data type.
 */
class FoodBackRecyclerViewAdapter(
    private val values: List<foodBank>
) : RecyclerView.Adapter<FoodBackRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.foodBankImg.setImageResource(item.image)
        holder.foodBankAdd.text = item.address
        holder.foodBankTitle.text = item.name

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val foodBankImg:ImageView
        val foodBankTitle:TextView
        val foodBankAdd:TextView

        init {

            foodBankImg  = itemView.findViewById(R.id.ivFoodBankImg)
            foodBankTitle = itemView.findViewById(R.id.tvFoodBankName)
            foodBankAdd = itemView.findViewById(R.id.tvAdd)
        }

    }




}