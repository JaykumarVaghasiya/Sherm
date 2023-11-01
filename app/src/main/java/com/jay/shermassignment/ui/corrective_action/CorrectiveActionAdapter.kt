package com.jay.shermassignment.ui.corrective_action

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDivider
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.response.correctiveaction.Row
import java.text.SimpleDateFormat
import java.util.Locale

class CorrectiveActionAdapter(
    private val context: Context,
    private val correctiveActionListener: OnCorrectiveActionItemClick,
    private val correctiveEvaluationListener: OnCorrectiveEvaluationClick,
    private val onDataSetChanged: (Int) -> Unit
) :
    RecyclerView.Adapter<CorrectiveActionAdapter.CorrectiveActionViewHolder>() {
    private var correctiveActionList = mutableListOf<Row>()

    inner class CorrectiveActionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val status: MaterialTextView = itemView.findViewById(R.id.tvCorrectiveStatus)
        private val date: MaterialTextView = itemView.findViewById(R.id.tvCorrectiveDate)
        private val assign: MaterialTextView = itemView.findViewById(R.id.tvCorrectiveAssign)
        private val assigner: MaterialTextView = itemView.findViewById(R.id.tvCorrectiveAssigner)
        private val divider:MaterialDivider=itemView.findViewById(R.id.caDivider)
        private val inspectionName: MaterialTextView =
            itemView.findViewById(R.id.tvInspectionInCorrectiveActionName)
        private val correctiveEvolution: LinearLayout =
            itemView.findViewById(R.id.btCorrectiveEvolution)

        fun bind(row: Row) {
            status.text = row.status
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDueDate = dateFormatter.parse(row.dueDate)
            val formattedDueDateString = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(formattedDueDate!!)
            date.text = formattedDueDateString
            assign.text = row.responsible
            assigner.text = row.reported
            inspectionName.text = row.caType
            if(status.text =="Closed"){
                divider.visibility=View.GONE
                correctiveEvolution.visibility=View.GONE
            }
            correctiveEvolution.setOnClickListener {
                correctiveEvaluationListener.onCorrectiveEvaluationClick(row)
            }

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
        holder.itemView.setOnClickListener {
            correctiveActionListener.onItemClick(correctiveAction)
        }

    }

    override fun getItemCount(): Int {
        return correctiveActionList.size

    }

    fun submitInspectionList(newCorrectiveAction: List<Row>) {
        correctiveActionList.clear()
        correctiveActionList.addAll(newCorrectiveAction)
        onDataSetChanged(correctiveActionList.size)
        Log.d("Debug", "newInspectionList size: ${newCorrectiveAction.size}")
        notifyDataSetChanged()
    }

    fun checkStatus(row: Row){

    }

    interface OnCorrectiveActionItemClick {
        fun onItemClick(row: Row)
    }
    interface OnCorrectiveEvaluationClick {
        fun onCorrectiveEvaluationClick(row: Row)
    }
}