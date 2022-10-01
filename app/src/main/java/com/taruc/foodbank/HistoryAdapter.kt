package com.taruc.foodbank

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.taruc.foodbank.databinding.FragmentItemBinding
import com.taruc.foodbank.entity.history


class HistoryAdapter(
    private var historyList: ArrayList<history>,

): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    private lateinit var db: FirebaseFirestore
    override fun getItemCount(): Int {
        return historyList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.history_item,parent,false)
        return ViewHolder(itemHolder)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = historyList[position]

        holder.foodName.text = item.food.toString()
        holder.userEmail.text = item.userEmail.toString()
        holder.time.text = item.timeTake.toString()


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val userEmail:TextView = itemView.findViewById(R.id.tvUserEmail)
        val foodName = itemView.findViewById<TextView>(R.id.lvFoodNameHistory)
        val time = itemView.findViewById<TextView>(R.id.tvTime)

    }
}