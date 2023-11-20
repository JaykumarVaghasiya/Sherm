package com.jay.shermassignment.ui.corrective_action

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jay.shermassignment.R
import com.jay.shermassignment.dataModel.correctiveaction.Row
import com.jay.shermassignment.databinding.ActivityCorrectiveActionBinding
import com.jay.shermassignment.di.viewmodels.correctiveaction.CorrectiveActionViewModel
import com.jay.shermassignment.generic.showConfirmationDialog
import com.jay.shermassignment.ui.add_corrective_action.AddCorrectiveAction
import com.jay.shermassignment.ui.corrective_action_details.CAViewActivity
import com.jay.shermassignment.ui.corrective_evaluation.CorrectiveEvaluation

class CorrectiveAction : AppCompatActivity(), CorrectiveActionAdapter.OnCorrectiveActionItemClick,
    CorrectiveActionAdapter.OnCorrectiveEvaluationClick {

    private lateinit var correctiveActionAdapter: CorrectiveActionAdapter
    private lateinit var binding: ActivityCorrectiveActionBinding
    private lateinit var correctiveActionViewModel: CorrectiveActionViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCorrectiveActionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        correctiveActionViewModel=ViewModelProvider(this)[CorrectiveActionViewModel::class.java]
        setupAdapter()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        loadCorrectiveAction()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.ispection_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> openAddCorrectiveActionView()
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupAdapter() {
        correctiveActionAdapter = CorrectiveActionAdapter(this, this, this) { itemcount ->
            updateActionBarItemCount(itemcount)

        }
        val inspectionLayoutManager = LinearLayoutManager(this)
        binding.rvCorrectiveAction.layoutManager = inspectionLayoutManager
        binding.rvCorrectiveAction.adapter = correctiveActionAdapter

        correctiveActionAdapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmpty()
            }

            fun checkEmpty() {
                binding.emptyListMessage.visibility =
                    (if (correctiveActionAdapter.itemCount == 0) View.VISIBLE else View.GONE)
            }
        })
    }

    private fun updateActionBarItemCount(itemCount: Int) {
        val actionBar = supportActionBar
        actionBar?.title = "Corrective Action ($itemCount)"
    }

    private fun setupListeners() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun loadCorrectiveAction() {
        val inspectionId = intent.getIntExtra("ids", 1)

        correctiveActionViewModel.correctiveAction(inspectionId)
        correctiveActionViewModel._correctiveActionLiveData.observe(this){correctiveAction->
            if(correctiveAction?.isSuccess == true){
                correctiveActionAdapter.submitInspectionList(correctiveAction.content.rows)
                binding.overLay.loadingScreen.visibility=View.GONE
            }else{
                showConfirmationDialog(getString(R.string.error),getString(R.string.empty_data_or_response))
            }
        }
        correctiveActionViewModel._errorMessageLiveData.observe(this){error->
            if(error != null){
                showConfirmationDialog(getString(R.string.error),error)
            }
        }
    }

    private fun openAddCorrectiveActionView() {
        val iId = intent.getIntExtra("ids", 0)
        val intent = Intent(this, AddCorrectiveAction::class.java)
        intent.putExtra("iId",iId)
        startActivity(intent)

    }

    override fun onItemClick(row: Row) {
        val id = row.id
        val iId = intent.getIntExtra("ids", 0)
        val intent = Intent(this, CAViewActivity::class.java)
        intent.putExtra("iId",iId)
        intent.putExtra("correctiveActionId", id)
        startActivity(intent)
    }

    override fun onCorrectiveEvaluationClick(row: Row) {
        val intent = Intent(this, CorrectiveEvaluation::class.java)
        val iId=intent.getIntExtra("ids",0)
        intent.putExtra("iId",iId)
        intent.putExtra("correctiveActionId", row.id)
        startActivity(intent)
    }


}
