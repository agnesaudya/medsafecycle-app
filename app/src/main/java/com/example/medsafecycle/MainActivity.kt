package com.example.medsafecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.medsafecycle.landing.LandingActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main_guest)

        Handler().postDelayed({
            val moveIntent = Intent(this@MainActivity, LandingActivity::class.java)
            startActivity(moveIntent)
            finish()
        }, 2000)
    }
}