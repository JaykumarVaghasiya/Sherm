package com.jay.shermassignment.ui.correctiveaction

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.model.correctiveaction.Row

class CorrectiveActionAdapter(private val context: Context) :
    RecyclerView.Adapter<CorrectiveActionAdapter.CorrectiveActionViewHolder>() {
    private var correctiveActionList = mutableListOf<Row>()

    inner class CorrectiveActionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val status: MaterialTextView = itemView.findViewById(R.id.tvCorrectiveStatus)
        private val date: MaterialTextView = itemView.findViewById(R.id.tvCorrectiveDate)
        private val assign: MaterialTextView = itemView.findViewById(R.id.tvCorrectiveAssign)
        private val assigner: MaterialTextView = itemView.findViewById(R.id.tvCorrectiveAssigner)
        private val inspectionName: MaterialTextView =
            itemView.findViewById(R.id.tvInspectionInCorrectiveActionName)
        private val correctiveEvolution: MaterialButton =
            itemView.findViewById(R.id.btCorrectiveEvolution)

        fun bind(row: Row) {
            status.text = row.status
            date.text = row.dueDate
            assign.text = row.responsible
            assigner.text = row.reported
            inspectionName.text = row.caType
            correctiveEvolution.setOnClickListener { }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CorrectiveActionAdapter.CorrectiveActionViewHolder {

        val view =
            LayoutInflater.from(context).inflate(R.layout.itemlist_corrective_action, parent, false)
        return CorrectiveActionViewHolder(view)

    }

    override fun onBindViewHolder(
        holder: CorrectiveActionAdapter.CorrectiveActionViewHolder,
        position: Int
    ) {
        val correctiveAction = correctiveActionList[position]
        holder.bind(correctiveAction)

    }

    override fun getItemCount(): Int {
        return correctiveActionList.size

    }
    fun submitInspectionList(newCorrectiveAction: List<Row>){
        correctiveActionList.addAll(newCorrectiveAction)
        Log.d("Debug", "newInspectionList size: ${newCorrectiveAction.size}")
        notifyDataSetChanged()
    }
}