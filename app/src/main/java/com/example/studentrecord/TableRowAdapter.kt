package com.example.studentrecord

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentrecord.arch.MainViewModel
import com.example.studentrecord.database.entity.ItemEntity
import java.util.*
import kotlin.collections.ArrayList

class TableRowAdapter(val viewModel: MainViewModel) : RecyclerView.Adapter<TableRowAdapter.ViewHolder>() {

    var userArrayList: ArrayList<ItemEntity> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList:ArrayList<ItemEntity>){
        userArrayList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.table_row_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val itemEntity = userArrayList[i];
        viewHolder.tvUserName.text = itemEntity.name
        viewHolder.tvEmail.text = itemEntity.name
        viewHolder.tvClass.text = itemEntity.class_
        viewHolder.tvLocation.text = itemEntity.location
        viewHolder.tvDateOfBirth.text = itemEntity.dateOfBirth
        viewHolder.itemView.setOnLongClickListener {
            viewModel.deleteItem(itemEntity)
            return@setOnLongClickListener true
        }

    }

    override fun getItemCount(): Int {
        return userArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserName: TextView = itemView.findViewById(R.id.tv_user_name)
        val tvEmail: TextView = itemView.findViewById(R.id.tv_user_email)
        val tvClass: TextView = itemView.findViewById(R.id.tv_user_class)
        val tvLocation: TextView = itemView.findViewById(R.id.tv_user_location)
        val tvDateOfBirth: TextView = itemView.findViewById(R.id.tv_user_date_of_birth)
    }
}
