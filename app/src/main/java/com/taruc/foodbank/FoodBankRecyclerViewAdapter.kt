package com.taruc.foodbank

import android.content.Intent
import android.content.Intent.getIntent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import com.taruc.foodbank.databinding.FragmentItemBinding
import com.taruc.foodbank.entity.food
import com.taruc.foodbank.entity.foodBank
import com.taruc.foodbank.entity.role
import com.taruc.foodbank.entity.user

/**
 * [RecyclerView.Adapter] that can display a [foodBank].
 * TODO: Replace the implementation with code for your data type.
 */
class FoodBankRecyclerViewAdapter(
    private val values: List<foodBank>
) : RecyclerView.Adapter<FoodBankRecyclerViewAdapter.ViewHolder>() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        auth = FirebaseAuth.getInstance()
        db = Firebase.firestore




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

        holder.foodBankTitle.text = item.name
        holder.foodBankSelect.setOnClickListener {
            Log.i("Button","click button"+item.name)
            val intent = Intent(holder.itemView.context, admin_Activity_FoodBank::class.java)
            intent.putExtra("foodBankName",item.name)
            holder.itemView.context.startActivity(intent)

        }
        holder.btDelete.setOnClickListener{
            Log.i("Delete","click button"+item.name)

            val intent = Intent(holder.itemView.context, admin_Activity_Delete::class.java)
            intent.putExtra("foodBankName",item.name)
            holder.itemView.context.startActivity(intent)

        }




    }



    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val foodBankImg:ImageView
        val foodBankTitle:TextView
        //val foodBankAdd:TextView
        val foodBankSelect:Button
        val btDelete:Button

        init {


            foodBankImg  = itemView.findViewById(R.id.ivFoodBankImg)
            foodBankTitle = itemView.findViewById(R.id.tvFoodBankName)
            //foodBankAdd = itemView.findViewById(R.id.tvAdd)
            foodBankSelect = itemView.findViewById(R.id.btSelect)
            btDelete = itemView.findViewById(R.id.btDelete)

            val user = auth.currentUser
            val userRef = db.collection("users").document(user?.email.toString())
            userRef.get().addOnSuccessListener {
                if (it.exists()){
                    val userObj = it.toObject(com.taruc.foodbank.entity.user::class.java)!!
                    if (userObj.role == role.ADMIN){
                        btDelete.visibility = android.view.View.VISIBLE
                    }else{
                        btDelete.visibility = android.view.View.GONE
                    }
                }
            }
        }

    }




}