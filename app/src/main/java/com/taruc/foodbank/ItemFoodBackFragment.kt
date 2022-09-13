package com.taruc.foodbank

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taruc.foodbank.entity.food
import com.taruc.foodbank.entity.foodBank
import com.taruc.foodbank.entity.role
import com.taruc.foodbank.entity.user
import com.taruc.foodbank.placeholder.PlaceholderContent
import java.util.*

/**
 * A fragment representing a list of Items.
 */
class ItemFoodBackFragment : Fragment() {

    private val initFoodBank = listOf<foodBank>(
        foodBank("Food bank001","Address 001",
        foods = listOf(food("food001",1, Date(),user("ryan","ryan@gmail.com", role.USER),"food1")),null,"bank001"),

        foodBank("Food bank002","Address 001",
            foods = listOf(food("food001",1, Date(),user("ryan","ryan@gmail.com", role.USER),"food1")),null,"bank001"),

        foodBank("Food bank003","Address 001",
            foods = listOf(food("food001",1, Date(),user("ryan","ryan@gmail.com", role.USER),"food1")),null,"bank001"),
    )

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

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyItemFoodBackRecyclerViewAdapter(PlaceholderContent.ITEMS)
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ItemFoodBackFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}