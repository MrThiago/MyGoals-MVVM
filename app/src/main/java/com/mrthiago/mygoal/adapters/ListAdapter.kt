package com.mrthiago.mygoal.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mrthiago.mygoal.R
import com.mrthiago.mygoal.model.Goal
import kotlinx.android.synthetic.main.goal_row.view.*

class ListAdapter(val adapterOnClick : (Any) -> Unit): RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var goalList = emptyList<Goal>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.goal_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val goalItem = goalList[position]

        holder.itemView.imageView.setImageResource(R.drawable.goalbg)
        holder.itemView.titleTv.text = goalItem.title
        holder.itemView.dateTv.text = goalItem.creationDate.toString()
        holder.itemView.descriptionTv.text = goalItem.description

        holder.itemView.rootView.setOnClickListener{
            adapterOnClick(goalItem)
        }
    }

    override fun getItemCount(): Int {
        return goalList.size
    }

    fun setData(goalList: List<Goal>){
        this.goalList = goalList
        notifyDataSetChanged()
    }

}