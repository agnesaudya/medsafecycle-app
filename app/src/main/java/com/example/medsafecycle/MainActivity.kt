package com.example.medsafecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.medsafecycle.home.hospital.HospitalHomeActivityBase
import com.example.medsafecycle.landing.LandingActivity

class MainActivity : AppCompatActivity() {
    private lateinit var mUserPreference: UserPreference
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main_guest)
        mUserPreference = UserPreference(this)
        Handler().postDelayed({
            if (mUserPreference.getToken().toString().isEmpty()) {
                val moveIntent = Intent(this, LandingActivity::class.java)
                startActivity(moveIntent)
                finish()
            }else{
                val moveIntent = Intent(this, HospitalHomeActivityBase::class.java)
                startActivity(moveIntent)
                finish()
            }

        }, 2000)
    }
}