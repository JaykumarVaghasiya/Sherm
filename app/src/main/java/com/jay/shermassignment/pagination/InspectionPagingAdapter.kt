package com.jay.shermassignment.pagination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jay.shermassignment.databinding.ItemlistInspectionBinding
import com.jay.shermassignment.model.inspection.Row
import java.text.SimpleDateFormat
import java.util.Locale

class InspectionPagingAdapter(private val deleteListener: OnDeleteListener,
                              private val inspectionListener: OnInspectionListener
) :
    PagingDataAdapter<Row, InspectionPagingAdapter.InspectionViewHolder>(COMPARATOR) {
    private var inspectionList = mutableListOf<Row>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InspectionViewHolder {
        val binding =
            ItemlistInspectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InspectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InspectionViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.binding.tvInspectionId.text = item.inspectionId
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDueDate = dateFormatter.parse(item.dueDate)
            val formattedDueDateString =
                SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault()).format(formattedDueDate!!)
            holder.binding.tvInspectionDate.text = formattedDueDateString
            holder.binding.tvInspectionName.text = item.inspectionType
            holder.binding.tvInspectionLocation.text = item.inspectionLocation
            holder.binding.tvInspectionVolunteer.text = item.responsible
            holder.binding.tvInspectionStatus.text = item.status
            holder.binding.btDelete.setOnClickListener {
                deleteListener.onDeleteClicked(item)
            }
            holder.itemView.setOnClickListener {
                inspectionListener.onInspectionClicked(inspectionList[position])
            }
        }
    }
    override fun getItemCount(): Int {
        return inspectionList.size
    }


    class InspectionViewHolder(val binding: ItemlistInspectionBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Row>() {
            override fun areItemsTheSame(oldItem: Row, newItem: Row): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Row, newItem: Row): Boolean {
                return oldItem == newItem
            }

        }
    }
    fun deleteInspection(row: Row){
        inspectionList.remove(row)
        notifyDataSetChanged()
    }

    interface OnDeleteListener {
        fun onDeleteClicked(row: Row)
    }
    interface OnInspectionListener {
        fun onInspectionClicked(row: Row)
    }
}