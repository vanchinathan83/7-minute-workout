package com.vanchi.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vanchi.a7minuteworkout.databinding.ActivityHistoryBinding
import com.vanchi.a7minuteworkout.history.HistoryAdapter
import com.vanchi.a7minuteworkout.history.HistoryDAO
import com.vanchi.a7minuteworkout.history.HistoryEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    var binding: ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.historyToolbar)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "History"
        }
        binding?.historyToolbar?.setNavigationOnClickListener{
            onBackPressed()
        }

        val historyDao = (application as SevenMinuteWorkoutApp).db.historyDao()
        lifecycleScope.launch {
            historyDao.loadAllHistory().collect {
                val histories = ArrayList(it)
                setUpRecyclerView(histories,historyDao)
            }
        }
    }

    fun setUpRecyclerView(historyList: ArrayList<HistoryEntity>, historyDAO: HistoryDAO){
        if(historyList.isNotEmpty()){
            val historyAdapter = HistoryAdapter(historyList) {
                deleteId ->
                deleteRecordDialog(deleteId, historyDAO)
                }
            binding?.rvHistory?.adapter = historyAdapter
            binding?.rvHistory?.layoutManager = LinearLayoutManager(this)
            binding?.tvNoRecords?.visibility = View.GONE
            binding?.rvHistory?.visibility = View.VISIBLE
        }else{
            binding?.tvNoRecords?.visibility = View.VISIBLE
            binding?.rvHistory?.visibility = View.GONE
        }
    }

    fun deleteRecordDialog(id: Int, historyDAO: HistoryDAO){
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setTitle("Delete History")
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert)
        lifecycleScope.launch {
            historyDAO.loadHistoryById(id).collect {
                if(it != null){
                    alertDialogBuilder.setMessage("Do you want to delete history on ${it.date}")
                }
            }
        }

        alertDialogBuilder.setPositiveButton("Yes") { dialogInterface, _ ->
            lifecycleScope.launch {
                historyDAO.delete(HistoryEntity(id))
                Toast.makeText(applicationContext,
                    "Deleted Record Successfully!", Toast.LENGTH_SHORT).show()
            }
            dialogInterface.dismiss()
        }
        alertDialogBuilder.setNegativeButton("No"){dialogInterface, _ ->
            dialogInterface.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}