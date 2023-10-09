package com.jay.shermassignment.ui.inspectionUI

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.model.inspection.Row

class InspectionAdapter(
    private val context: Context,
    private val deleteListener: OnDeleteListener,
    private val inspectionListener: OnInspectionListener
) :
    RecyclerView.Adapter<InspectionAdapter.InspectionViewHolder>() {
    private var inspectionList= mutableListOf<Row>()
    inner class InspectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val inspectionId = itemView.findViewById<MaterialTextView>(R.id.tvInspectionId)
        private val dueDate = itemView.findViewById<MaterialTextView>(R.id.tvInspectionDate)
        private val inspectionType = itemView.findViewById<MaterialTextView>(R.id.tvInspectionName)
        private val inspectionLocation =
            itemView.findViewById<MaterialTextView>(R.id.tvInspectionLocation)
        private val responsible =
            itemView.findViewById<MaterialTextView>(R.id.tvInspectionVolunteer)
        private val status = itemView.findViewById<MaterialTextView>(R.id.tvInspectionStatus)
        private val delete = itemView.findViewById<MaterialButton>(R.id.btDelete)

        fun bind(row: Row) {

            inspectionId.text = row.inspectionId
            dueDate.text = row.dueDate
            inspectionType.text = row.inspectionType
            inspectionLocation.text = row.inspectionLocation
            responsible.text = row.responsible
            status.text = row.status

            delete.setOnClickListener {
                deleteListener.onDeleteClicked(row)
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InspectionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.itemlist_inspection, parent, false)
        return InspectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: InspectionViewHolder, position: Int) {
        val inspection = inspectionList[position]
        holder.bind(inspection)

        holder.itemView.setOnClickListener {
            inspectionListener.onInspectionClicked(inspectionList[position])
        }
    }

    override fun getItemCount(): Int {
        return inspectionList.size
    }

    fun submitInspectionList(newInspectionList: List<Row>){
        inspectionList.clear()
        inspectionList.addAll(newInspectionList)
        notifyDataSetChanged()
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