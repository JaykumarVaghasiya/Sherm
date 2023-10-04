package com.jay.shermassignment.dashboardUI

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.model.dashboard.Dashboard

class DashboardAdapter(
    private val context: Context,
    private val dashboardList: List<Dashboard>,
    private val  onItemClick:(Dashboard)-> Unit
) :
    RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.itemlist_dashboard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dashboard = dashboardList[position]
        holder.bind(dashboard)

        if (dashboard.name == "INSPECTIONS") {
            holder.itemView.setOnClickListener {
                onItemClick(dashboard)
            }
        }
    }

    override fun getItemCount(): Int {
        return dashboardList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvName: MaterialTextView =itemView.findViewById(R.id.tvName)
        private val dashboardImage: ImageView =itemView.findViewById(R.id.imageView)
        fun bind(dashboard: Dashboard) {
            tvName.text = dashboard.name
            dashboardImage.setImageResource(getImageResourceByName(dashboard.image))
        }

        private fun getImageResourceByName(imageName: String): Int {
            return context.resources.getIdentifier(imageName, "drawable", context.packageName)
        }
    }
}
