package com.taruc.foodbank

import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.taruc.foodbank.entity.Donation


class DonationAdapter (private val donationList: ArrayList<Donation>): RecyclerView.Adapter<DonationAdapter.DonationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.donation_item,
        parent,false)
        return DonationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DonationViewHolder, position: Int) {
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.email.toString()
        var userRole = ""

        val db = FirebaseFirestore.getInstance()
        val usersRef = db.collection("users")
        usersRef.document(uid).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document.exists()) {
                    val role = document.getString("role")
                    userRole = role.toString()
                } else {
                    Log.d(ContentValues.TAG, "The document doesn't exist.")
                }
            } else {
                task.exception?.message?.let {
                    Log.d(ContentValues.TAG, it)
                }
            }
        }

        val donation:Donation = donationList[position]
        holder.donateTo.text = donation.donateTo
        holder.food.text = donation.food
        holder.status.text = donation.status
        holder.created.text = donation.dateTimeCreated
        holder.card.setOnClickListener{
            val context = holder.itemView.context
            if(userRole == "USER"){
                val intent = Intent(context, UserDonationDetailsActivity ::class.java)
                intent.putExtra("dateTimeCreated",donationList[position].dateTimeCreated)
                context.startActivity(intent)
            }
            if(userRole == "ADMIN"){
            val intent = Intent(context, AdminDonationDetailsActivity ::class.java)
            intent.putExtra("dateTimeCreated",donationList[position].dateTimeCreated)
            context.startActivity(intent)
        }
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
        val status: TextView = itemView.findViewById(R.id.tvStatus)
        val food: TextView = itemView.findViewById(R.id.tvFood)
        val card:CardView = itemView.findViewById(R.id.card)
        val created:TextView = itemView.findViewById(R.id.tv_dtCreated)
    }

}