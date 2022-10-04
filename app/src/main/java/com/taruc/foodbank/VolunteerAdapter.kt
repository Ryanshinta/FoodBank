package com.taruc.foodbank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.taruc.foodbank.entity.event

class VolunteerAdapter(private val eventList: ArrayList<event>) : RecyclerView.Adapter<VolunteerAdapter.EventViewHolder>() {

    var onItemClick : ((event) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.grid_layout_user_event, parent, false)
        return EventViewHolder(itemHolder)


    }

    override fun getItemCount(): Int {
        return eventList.size

    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        var event = eventList[position]


//        holder.imgevent.setImageResource(event.image)
        holder.tvEvent.text = event.name
        holder.tvDuration.text = "Start From : "+event.dateStart

        holder.itemView.findViewById<Button>(R.id.btnSelect).setOnClickListener {
            onItemClick?.invoke(event)
        }


    }

    public class EventViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
       // var imgevent = itemView.findViewById<ImageView>(R.id.imgEvent)
        var tvEvent = itemView.findViewById<TextView>(R.id.tvEvent)
        var tvDuration = itemView.findViewById<TextView>(R.id.tvDuration)




    }

}