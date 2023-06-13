package com.example.medsafecycle.limbah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.medsafecycle.R

class LimbahNotFoundActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_limbah_not_found)
        val backButton : Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }
    }
}