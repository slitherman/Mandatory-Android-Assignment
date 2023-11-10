package com.example.mobilemandatoryassignmentbirthdaylist.Models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilemandatoryassignmentbirthdaylist.R

class PersonsAdapter<T>(
    private val Items: List<T>,
    private val onItemsClicked:(position:Int) -> Unit
): RecyclerView.Adapter<PersonsAdapter.MyViewHolder>()
{
    class MyViewHolder(itemView: View, private val onItemClicked: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {


        val textView: TextView = itemView.findViewById(R.id.textview_list_item)

        init {

            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = bindingAdapterPosition

            onItemClicked(position)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = Items[position]
        holder.textView.text = item.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val myView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_card,parent,false)
        return MyViewHolder(myView,onItemsClicked)
    }
    override fun getItemCount(): Int {
        return Items.size
    }

}