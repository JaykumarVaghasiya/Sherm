package com.jay.shermassignment

import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class ShowInspectionDetailsActivity : AppCompatActivity() {

    private lateinit var tvCategory:MaterialTextView
    private lateinit var tvInspectionType:MaterialTextView
    private lateinit var tvSite:MaterialTextView
    private lateinit var tvInspectionLocation:MaterialTextView
    private lateinit var tvResponsiblePerson:MaterialTextView
    private lateinit var tvDueDate:MaterialTextView
    private lateinit var tvReportingTo:MaterialTextView
    private lateinit var spCategory:Spinner
    private lateinit var spInspectionType:Spinner
    private lateinit var etSite:EditText
    private lateinit var spInspectionLocation:Spinner
    private lateinit var spResponsiblePerson:Spinner
    private lateinit var btDate:MaterialButton
    private lateinit var dpDueDate:DatePicker
    private lateinit var spReportingTo:Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_inspection_details)

        tvCategory=findViewById(R.id.tvCategory)
        tvInspectionType=findViewById(R.id.tvInspectionType)
        tvSite=findViewById(R.id.tvSite)
        tvInspectionLocation=findViewById(R.id.tvInspectionLocation)
        tvCategory=findViewById(R.id.tvCategory)
        tvResponsiblePerson=findViewById(R.id.tvResponsiblePerson)
        tvDueDate=findViewById(R.id.tvDueDate)
        tvReportingTo=findViewById(R.id.tvReportingTo)
        spCategory=findViewById(R.id.spCategory)
        spInspectionType=findViewById(R.id.spInspectionType)
        etSite=findViewById(R.id.etSite)
        btDate=findViewById(R.id.btCalender)
        spInspectionLocation=findViewById(R.id.spInspectionLocation)
        spResponsiblePerson=findViewById(R.id.spResponsiblePerson)
        spReportingTo=findViewById(R.id.spReportingTo)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.edit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.edit) {
            tvCategory.visibility= View.GONE
            spCategory.visibility= View.VISIBLE

            tvInspectionType.visibility=View.GONE
            spInspectionType.visibility=View.VISIBLE

            tvSite.visibility=View.GONE
            etSite.visibility=View.VISIBLE

            tvInspectionLocation.visibility=View.GONE
            spInspectionLocation.visibility=View.VISIBLE

            tvResponsiblePerson.visibility=View.GONE
            spResponsiblePerson.visibility=View.VISIBLE

            tvReportingTo.visibility=View.GONE
            spReportingTo.visibility=View.VISIBLE

        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialogue(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialogue_date)
        dialog.setCancelable(false)

        dpDueDate=dialog.findViewById(R.id.dpDueDate)

        val d
    }
}