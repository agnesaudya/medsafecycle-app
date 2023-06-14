package com.example.medsafecycle.limbah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.example.medsafecycle.R
import com.example.medsafecycle.viewmodel.GuestDetailViewModel
import com.example.medsafecycle.viewmodel.GuestViewModelFactory

class GuestDetailActivity : AppCompatActivity() {
    private lateinit var guestDetailViewModel: GuestDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_detail)

        guestDetailViewModel = obtainViewModel(this@GuestDetailActivity)

        val done : CardView = findViewById(R.id.selesai)
        done.setOnClickListener {
            finish()
        }
        val waste_id = intent.getStringExtra("waste_id")

        if (waste_id != null) {
            manageToolbar(waste_id)
        }
    }

    private fun manageToolbar(waste_id:String){
        val toolbar : Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        val inflater: LayoutInflater = LayoutInflater.from(this)
        val customToolbar = inflater.inflate(R.layout.basic_toolbar, toolbar, false)

        val back: ImageButton = customToolbar.findViewById(R.id.back_button)
        val barTitle: TextView = customToolbar.findViewById(R.id.toolbar_title)

        barTitle.text = "Detail Limbah"
        back.setOnClickListener {
            finish()
        }
        toolbar.addView(customToolbar)
    }

    private fun obtainViewModel(activity: AppCompatActivity): GuestDetailViewModel {
        val factory = GuestViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(GuestDetailViewModel::class.java)
    }
}