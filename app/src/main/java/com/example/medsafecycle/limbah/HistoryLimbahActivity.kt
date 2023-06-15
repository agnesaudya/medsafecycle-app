package com.example.medsafecycle.limbah

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medsafecycle.HistoryResponseItem
import com.example.medsafecycle.R
import com.example.medsafecycle.UserPreference
import com.example.medsafecycle.viewmodel.AuthViewModelFactory
import com.example.medsafecycle.viewmodel.HistoryViewModel

class HistoryLimbahActivity : AppCompatActivity() {

    private lateinit var rvLimbah: RecyclerView
    private val historyViewModel: HistoryViewModel by viewModels {
        AuthViewModelFactory(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_limbah)
        supportActionBar?.hide()

        rvLimbah = findViewById(R.id.rec_view_history)
        val layoutManager = LinearLayoutManager(this)
        rvLimbah.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        rvLimbah.addItemDecoration(itemDecoration)


        getData()
        manageToolbar()
    }

    private fun getData() {
        val adapter = HistoryLimbahAdapter()
        adapter.setOnItemClickCallback(object : HistoryLimbahAdapter.OnItemClickCallback {
            override fun onItemClicked(data: HistoryResponseItem) {
                val intentDetail = Intent(this@HistoryLimbahActivity, DetailLimbahActivity::class.java)
                intentDetail.putExtra("waste_id",data.wasteId)
                startActivity(intentDetail)
            }
        })

        rvLimbah.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        historyViewModel.history.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }


    private fun manageToolbar(){
        val toolbar : Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        val inflater: LayoutInflater = LayoutInflater.from(this)
        val customToolbar = inflater.inflate(R.layout.basic_toolbar, toolbar, false)

        val back: ImageButton = customToolbar.findViewById(R.id.back_button)
        val barTitle: TextView = customToolbar.findViewById(R.id.toolbar_title)

        barTitle.text = "Riwayat Scan"
        back.setOnClickListener {
            finish()
        }
        toolbar.addView(customToolbar)
    }

    // TODO : Jangan lupa sesuain ini sama output API, terutama bagian pas nambahin list

}