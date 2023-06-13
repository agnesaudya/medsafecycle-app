package com.example.medsafecycle.limbah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView

import com.example.medsafecycle.R

class DetailLimbahActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_limbah)

        val done : CardView = findViewById(R.id.selesai)
        done.setOnClickListener {
            finish()
        }

        manageToolbar()
    }

    private fun manageToolbar(){
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
}