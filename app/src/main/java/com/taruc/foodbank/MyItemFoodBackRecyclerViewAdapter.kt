package com.taruc.foodbank

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil

import com.taruc.foodbank.placeholder.PlaceholderContent.PlaceholderItem
import com.taruc.foodbank.databinding.FragmentItemBinding
import org.w3c.dom.Text

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemFoodBackRecyclerViewAdapter(
    private val values: List<PlaceholderItem>
) : RecyclerView.Adapter<MyItemFoodBackRecyclerViewAdapter.ViewHolder>() {
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
        holder.foodBankAdd.text
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