package com.taruc.foodbank

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.firestore.FirebaseFirestore
import com.taruc.foodbank.entity.foodBank
import java.util.ArrayList

/**
 * A fragment representing a list of Items.
 */
class ItemFoodBankFragment : Fragment() {

    private lateinit var adapter: FoodBankRecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var foodBankList: ArrayList<foodBank>
    private lateinit var db: FirebaseFirestore


    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        //val binding = FragmentMenuBinding.inflate(inflater, container, false)
        db = FirebaseFirestore.getInstance()
        val button:Button = view.findViewById(R.id.btSelect)
        db.collection("foodbanks").get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    for (data in it.documents){
                        val foodBankObj:foodBank? = data.toObject(foodBank::class.java)
                        if (foodBankObj != null){
                            foodBankList.add(foodBankObj)
                        }
                    }
                }
            }
            .addOnFailureListener{
                //Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = FoodBankRecyclerViewAdapter(foodBankList)
            }
        }

        button.setOnClickListener {
            Log.i("Button","click button")
        }

        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ItemFoodBankFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

}