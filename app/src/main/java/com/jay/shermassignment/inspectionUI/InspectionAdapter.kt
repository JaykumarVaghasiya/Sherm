package com.jay.shermassignment.inspectionUI

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.model.inspection.Row

class InspectionAdapter(
    private val context: Context,
    private val inspectionList: List<Row>
) :
    RecyclerView.Adapter<InspectionAdapter.InspectionViewHolder>() {

    inner class InspectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val inspectionId = itemView.findViewById<MaterialTextView>(R.id.tvInspectionId)
        private val dueDate = itemView.findViewById<MaterialTextView>(R.id.tvInspectionDate)
        private val inspectionType = itemView.findViewById<MaterialTextView>(R.id.tvInspectionName)
        private val inspectionLocation =
            itemView.findViewById<MaterialTextView>(R.id.tvInspectionLocation)
        private val responsible =
            itemView.findViewById<MaterialTextView>(R.id.tvInspectionVolunteer)
        private val status = itemView.findViewById<MaterialTextView>(R.id.tvInspectionStatus)
        private val delete = itemView.findViewById<MaterialTextView>(R.id.btDelete)

        fun bind(row: Row) {

            inspectionId.text = row.inspectionId
            dueDate.text = row.dueDate
            inspectionType.text = row.inspectionType
            inspectionLocation.text = row.inspectionLocation
            responsible.text = row.responsible
            status.text = row.responsible


            delete.setOnClickListener {

            }

        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InspectionAdapter.InspectionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.itemlist_inspection, parent, false)
        return InspectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: InspectionAdapter.InspectionViewHolder, position: Int) {
        val inspection = inspectionList[position]
        holder.bind(inspection)
    }

    override fun getItemCount(): Int {
        return inspectionList.size
    }
}