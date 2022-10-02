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
import com.taruc.foodbank.entity.volunteer

class VolunteerListAdapter (private val volunteerList: ArrayList<volunteer>): RecyclerView.Adapter<VolunteerListAdapter.VolunteerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolunteerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.volunteer_user,
            parent,false)
        return VolunteerViewHolder(itemView)
    }

    class VolunteerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvVolieName)
        val email: TextView = itemView.findViewById(R.id.tvVolieEmail)
        val status: TextView = itemView.findViewById(R.id.tvStatus)
        val card: CardView = itemView.findViewById(R.id.card)

    }

    override fun onBindViewHolder(holder: VolunteerViewHolder, position: Int) {
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

        val volunteer:volunteer = volunteerList[position]
        holder.name.text = volunteer.name
        holder.email.text = volunteer.email
        holder.status.text = volunteer.status
        holder.card.setOnClickListener{
            val context = holder.itemView.context
            if(userRole == "USER"){
                val intent = Intent(context, UserVolunteering ::class.java)
                    .putExtra("name",volunteer.name.toString())
                context.startActivity(intent)
            }
            if(userRole == "ADMIN"){
                val intent = Intent(context, adminViewVolunteer ::class.java)
                    .putExtra("name",volunteer.name.toString())
                context.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int {
        return volunteerList.size
    }
}