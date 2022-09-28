package com.taruc.foodbank

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
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
        holder.card.setOnClickListener{
            Log.i("Card", "Card Clicked")
        }
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
        val status: TextView = itemView.findViewById(R.id.tvState)
        val food: TextView = itemView.findViewById(R.id.tvFood)
        val card:CardView = itemView.findViewById(R.id.card)
    }

}