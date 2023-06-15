package com.example.medsafecycle.limbah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medsafecycle.R
import com.example.medsafecycle.viewmodel.guest.GuestHomeViewModel
import com.example.medsafecycle.viewmodel.GuestViewModelFactory
import com.example.medsafecycle.viewmodel.guest.GuestLimbahHistoryViewModel

class GuestLimbahHistoryActivity : AppCompatActivity() {
    private lateinit var rvLimbah: RecyclerView
    private lateinit var guestLimbahHistoryViewModel: GuestLimbahHistoryViewModel
    private lateinit var adapter: GuestHistoryLimbahAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_limbah_history)
        supportActionBar?.hide()
        manageToolbar()
        guestLimbahHistoryViewModel = obtainViewModel(this)

        guestLimbahHistoryViewModel.getAllLimbah().observe(this){
                limbahList ->
            if (limbahList  != null) {
                adapter.setListLimbah(limbahList)
                adapter.notifyDataSetChanged()
            }
        }

        rvLimbah = findViewById(R.id.rec_view_history)

        adapter = GuestHistoryLimbahAdapter()
        rvLimbah.layoutManager = LinearLayoutManager(this)
        rvLimbah.setHasFixedSize(true)
        rvLimbah.adapter = adapter

    }

    private fun obtainViewModel(activity: AppCompatActivity): GuestLimbahHistoryViewModel {
        val factory = GuestViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[GuestLimbahHistoryViewModel::class.java]
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
}