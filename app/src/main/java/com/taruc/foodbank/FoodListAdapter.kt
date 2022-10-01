package com.taruc.foodbank

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.taruc.foodbank.entity.foodBank
import java.time.LocalDateTime

class FoodListAdapter(
    private val foodList: Map<String, Int>,
    listName: List<String>,
    foodBankName: String
) : RecyclerView.Adapter<FoodListAdapter.FoodListViewHolder>(){
    var listName = listName
    var foodBankName = foodBankName
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListViewHolder {
        val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.food_item,parent,false)
        return FoodListViewHolder(itemHolder)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: FoodListViewHolder, position: Int) {
        auth = FirebaseAuth.getInstance()
        holder.foodName.text = listName.get(position)
        holder.foodQty.text = foodList.get(listName.get(position)).toString()
        holder.btTake.setOnClickListener {
            db = FirebaseFirestore.getInstance()
            db.collection("foodbanks").whereEqualTo("name",foodBankName).get().addOnSuccessListener {
                for (document in it.documents){

                    val fbId = document.id
                    val map = mapOf("foods.${listName.get(position)}" to foodList.get(listName.get(position))!!.toInt() - 1)
                    if (foodList.get(listName.get(position))!!.toInt() == 0){
                        val delete = mapOf(
                            listName.get(position) to FieldValue.delete()
                        )
                        db.collection("foodbanks").document(fbId).update(delete)

                    }else{
                        db.collection("foodbanks").document(fbId).update(map)
                        val addHistory = mapOf(
                            "food" to listName.get(position),
                            "foodbankName" to foodBankName,
                            "timeTake" to LocalDateTime.now().toString(),
                            "userEmail" to auth.currentUser?.email.toString()
                        )
                        db.collection("foodHistory").document().set(addHistory)
                    }


                }


            }

        }
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    class FoodListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var foodName: TextView = itemView.findViewById<TextView>(R.id.tvFoodName)
        var foodQty: TextView = itemView.findViewById<TextView>(R.id.tvQty)
        var btTake: Button = itemView.findViewById<Button>(R.id.btTake)
        var ve = itemView
    }

}