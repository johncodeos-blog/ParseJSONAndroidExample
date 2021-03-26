package com.example.parsejsonexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parsejsonexample.databinding.CellBinding

class RVAdapter(private val cell: ArrayList<Cell>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ItemViewHolder(var viewBinding: CellBinding) : RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as ItemViewHolder
        itemViewHolder.viewBinding.employeeIdTextview.text = cell[position].employeeId
        itemViewHolder.viewBinding.employeeNameTextview.text = cell[position].employeeName
        itemViewHolder.viewBinding.employeeSalaryTextview.text = cell[position].employeeSalary
        itemViewHolder.viewBinding.employeeAgeTextview.text = cell[position].employeeAge
    }

    override fun getItemCount(): Int {
        return cell.size
    }
}