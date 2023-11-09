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
           holder.textInspectionId.text = item.inspectionId
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDueDate = dateFormatter.parse(item.dueDate)
            val formattedDueDateString =
                SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault()).format(formattedDueDate!!)
            holder.textInspectionDate.text = formattedDueDateString
            holder.textInspectionName.text = item.inspectionType
            holder.textInspectionLocation.text = item.inspectionLocation
            holder.textInspectionVolunteer.text = item.responsible
            holder.textInspectionStatus.text = item.status
            holder.buttonDelete.setOnClickListener {
                deleteListener.onDeleteClicked(item)
            }
            holder.itemView.setOnClickListener {
              item?.let { inspectionListener.onInspectionClicked(it) }
            }
        }
    }
    override fun getItemCount(): Int {
        return inspectionList.size
    }


    class InspectionViewHolder(binding: ItemlistInspectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val textInspectionId = binding.tvInspectionId
        val textInspectionDate=binding.tvInspectionDate
        val textInspectionName=binding.tvInspectionName
        val textInspectionLocation=binding.tvInspectionLocation
        val textInspectionVolunteer=binding.tvInspectionVolunteer
        val textInspectionStatus=binding.tvInspectionStatus
        val buttonDelete=binding.btDelete
    }

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
    fun deleteInspection(row: Row) {
        val position = inspectionList.indexOf(row)
        if (position != -1) {
            inspectionList.remove(row)
            notifyItemRemoved(position)
        }
    }

    interface OnDeleteListener {
        fun onDeleteClicked(row: Row)
    }
    interface OnInspectionListener {
        fun onInspectionClicked(row: Row)
    }
}