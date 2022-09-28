package com.taruc.foodbank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.taruc.foodbank.entity.food

class FoodListAdapter (private val foodList:ArrayList<food>) : RecyclerView.Adapter<FoodListAdapter.FoodListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListViewHolder {
        val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.grid_layout_user_event,parent,false)
        return FoodListViewHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: FoodListViewHolder, position: Int) {
        var food: food = foodList[position]

        holder.foodName.text = food.name
        holder.foodQty.text = food.amount.toString()
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    class FoodListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var foodName = itemView.findViewById<TextView>(R.id.tvFood)
        var foodQty = itemView.findViewById<TextView>(R.id.tvQty)
    }

}