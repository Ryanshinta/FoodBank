package com.taruc.foodbank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.google.type.DateTime
import com.taruc.foodbank.entity.Donation

class DonationAdapter (private val donationList: ArrayList<Donation>): RecyclerView.Adapter<DonationAdapter.DonationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.donation_item,
        parent,false)
        return DonationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DonationViewHolder, position: Int) {
        val donation:Donation = donationList[position]
        holder.donateTo.text = donation.donateTo
        holder.food.text = donation.food
        holder.status.text = donation.status
    }

    override fun getItemCount(): Int {
        return donationList.size
    }

    class DonationViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val donateTo: TextView = itemView.findViewById(R.id.tvDonateTo)
//        val donorEmail:
//        val foodQty:
//        val foodType:
//        val pickupAddress:
//        val pickupDateTime:
        val status: TextView = itemView.findViewById(R.id.tvStatus)
        val food: TextView = itemView.findViewById(R.id.tvFood)
    }

}