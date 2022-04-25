package com.example.recycleviewproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class EmployeeAdapter(private val deleteAction: (Int) -> Unit) :
    RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    private var employees = mutableListOf<Employee>()

    class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val photoImageView: ImageView = itemView.findViewById(R.id.photo)
        val fullNameTextView: TextView = itemView.findViewById(R.id.name)
        val departmentTextView: TextView = itemView.findViewById(R.id.department)
        val deleteButton: ImageView = itemView.findViewById(R.id.delete_button)
        val likeButton: ImageView = itemView.findViewById(R.id.like_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.employee_list_item, parent, false)
        return EmployeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = employees[position]
        with(holder){
            fullNameTextView.text = employee.fullName
            departmentTextView.text = employee.department

            Glide.with(photoImageView.context)
                .load(employee.photoUrl)
                .centerCrop()
                .into(photoImageView)

            deleteButton.setOnClickListener{
                deleteAction(position)
            }

            likeButton.setOnClickListener{
                if(employee.isLiked){
                    likeButton.setImageResource(R.drawable.hollow_heart)
                    employee.isLiked = false
                }
                else{
                    likeButton.setImageResource(R.drawable.heart)
                    employee.isLiked = true
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return employees.size
    }

    fun reload(data: List<Employee>){
        val diffUtilCallback = EmployeesDiffUtilCallback(employees, data)
        val employeesDiffResult = DiffUtil.calculateDiff(diffUtilCallback)
        employees = data.toMutableList()
        employeesDiffResult.dispatchUpdatesTo(this)
    }
}