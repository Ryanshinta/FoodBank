package com.taruc.foodbank

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.taruc.foodbank.entity.event

class EventAdapter(private val eventList: ArrayList<event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    var onItemClick : ((event) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.grid_layout_user_event, parent, false)
        return EventViewHolder(itemHolder)

    }

    override fun getItemCount(): Int {
        return eventList.size

    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        var event: event = eventList[position]


//        holder.imgevent.setImageResource(event.image)
        holder.tvEvent.text=event.name

        holder.itemView.findViewById<Button>(R.id.btnSelect).setOnClickListener {
            onItemClick?.invoke(event)
        }


    }

    public class EventViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var imgevent = itemView.findViewById<ImageView>(R.id.imgEvent)
        var tvEvent = itemView.findViewById<TextView>(R.id.tvEvent)




    }

}