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
import com.taruc.foodbank.entity.Application
import com.taruc.foodbank.entity.Donation


class ApplicationAdapter (private val applicationList: ArrayList<Application>): RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.application_item,
            parent,false)
        return ApplicationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ApplicationViewHolder, position: Int) {
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

        val application:Application = applicationList[position]
        holder.resources.text = application.resourcesName
        holder.amount.text = application.resourcesAmount.toString()
        holder.status.text = application.applicationStatus
        holder.created.text = application.dateTimeApplied
        holder.card.setOnClickListener{
            val context = holder.itemView.context
            if(userRole == "USER"){
                val intent = Intent(context, UserApplicationDetailsActivity ::class.java)
                intent.putExtra("dateTimeApplied",applicationList[position].dateTimeApplied)
                context.startActivity(intent)
            }
            if(userRole == "ADMIN"){
                val intent = Intent(context, AdminApplicationDetailsActivity ::class.java)
                intent.putExtra("dateTimeApplied",applicationList[position].dateTimeApplied)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return applicationList.size
    }

    class ApplicationViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
//        val applyBy: TextView = itemView.findViewById(R.id.tv_ApplyBy)
        //        val donorEmail:
//        val foodQty:
//        val foodType:
//        val pickupAddress:
//        val pickupDateTime:
        val amount: TextView = itemView.findViewById(R.id.tvAmount)
        val status: TextView = itemView.findViewById(R.id.tvApplicationStatus)
        val resources: TextView = itemView.findViewById(R.id.tvResource)
        val card:CardView = itemView.findViewById(R.id.applicationCard)
        val created:TextView = itemView.findViewById(R.id.tv_dtApplied)
    }

}