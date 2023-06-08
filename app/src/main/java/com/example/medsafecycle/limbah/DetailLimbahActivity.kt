package com.example.medsafecycle.limbah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.medsafecycle.R

class DetailLimbahActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_limbah)

        supportActionBar?.hide()

        val done : Button = findViewById(R.id.selesai)
        done.setOnClickListener {
            finish()
        }

    }
}