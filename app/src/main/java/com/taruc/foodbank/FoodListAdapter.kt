package com.taruc.foodbank

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.taruc.foodbank.entity.food

class FoodListAdapter (private val foodList:Map<String,Int>,listName: List<String>) : RecyclerView.Adapter<FoodListAdapter.FoodListViewHolder>(){
    var listName = listName
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListViewHolder {
        val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.food_item,parent,false)
        return FoodListViewHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: FoodListViewHolder, position: Int) {
//        Log.e("List Name",listName.toString())
//        Log.e("Food Name",listName.get(position))
//        Log.e("Food qty",foodList.get(listName.get(position)).toString())

       // var foodMap: String = foodList.containsKey()
        holder.foodName.text = listName.get(position)
        holder.foodQty.text = foodList.get(listName.get(position)).toString()
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    class FoodListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var foodName: TextView = itemView.findViewById<TextView>(R.id.tvFoodName)
        var foodQty: TextView = itemView.findViewById<TextView>(R.id.tvQty)
    }

}