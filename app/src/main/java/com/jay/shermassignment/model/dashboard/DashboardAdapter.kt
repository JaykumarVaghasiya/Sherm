package com.jay.shermassignment.model.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jay.shermassignment.R

import android.content.Context
import android.widget.ImageView
import com.google.android.material.textview.MaterialTextView

class DashboardAdapter(private val context: Context, private val dashboardList: List<Dashboard>) :
    RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.itemlist_dashboard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dashboard = dashboardList[position]
        holder.bind(dashboard)
    }

    override fun getItemCount(): Int {
        return dashboardList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvName=itemView.findViewById<MaterialTextView>(R.id.tvName)
        val dashboardImage=itemView.findViewById<ImageView>(R.id.imageView)
        fun bind(dashboard: Dashboard) {
            tvName.text = dashboard.name
            dashboardImage.setImageResource(getImageResourceByName(dashboard.image))
        }

        private fun getImageResourceByName(imageName: String): Int {
            return context.resources.getIdentifier(imageName, "drawable", context.packageName)
        }
    }
}
