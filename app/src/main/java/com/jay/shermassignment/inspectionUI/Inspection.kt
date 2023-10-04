package com.jay.shermassignment.inspectionUI

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jay.shermassignment.R
import com.jay.shermassignment.model.inspection.InspectionRef
import com.jay.shermassignment.model.inspection.Row
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class Inspection : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var inspectionAdapter: InspectionAdapter
    private val inspectionList:List<Row> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspection)

        recyclerView = findViewById(R.id.rvInspection)
        val inspectionLayoutManager=LinearLayoutManager(this)
        recyclerView.layoutManager=inspectionLayoutManager
        inspectionAdapter= InspectionAdapter(this,inspectionList)

        lifecycleScope.launch {
            val inspectionResponse=try {
                InspectionDetailsInstance.api.getInspectionDetails(
                    InspectionRef(
                    "","",1,Any(),"completedDate", arrayListOf(),"asc","false"
                )
                )
            }catch (e:IOException){
                Toast.makeText(this@Inspection,e.message,Toast.LENGTH_SHORT).show()
                return@launch
            }catch (e:HttpException){
                Toast.makeText(this@Inspection,e.message,Toast.LENGTH_SHORT).show()
                return@launch
            }
            if(inspectionResponse.isSuccessful && inspectionResponse.body() != null){
                recyclerView.adapter=inspectionAdapter
            }else{
                Toast.makeText(this@Inspection,R.string.empty_data_or_response,Toast.LENGTH_LONG).show()
            }
        }
    }
}