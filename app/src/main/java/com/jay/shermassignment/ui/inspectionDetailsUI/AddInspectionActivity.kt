package com.jay.shermassignment.ui.inspectionDetailsUI

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.jay.shermassignment.R

class AddInspectionActivity : AppCompatActivity() {

    private lateinit var categorySpinner: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_inspection_actitvtity)

        categorySpinner = findViewById(R.id.spCategory)


        ArrayAdapter.createFromResource(
                this,
                R.array.category,
                android.R.layout.simple_spinner_item
            ).also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                AlertDialog.Builder(this)
                    .setTitle(R.string.category)
                    .setAdapter(arrayAdapter) { _, position ->
                        val selectedItem = resources.getStringArray(R.array.category)[position]
                    }
                    .show()

            }
        }
    }

