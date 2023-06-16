package com.example.medsafecycle.home.hospital

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.medsafecycle.R
import com.example.medsafecycle.UserPreference
import com.example.medsafecycle.databinding.ActivityHospitalHomeBaseBinding
import com.example.medsafecycle.landing.LandingActivity

class HospitalHomeActivityBase : AppCompatActivity() {

    private lateinit var binding: ActivityHospitalHomeBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHospitalHomeBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_hospital_home_base)
        navView.setupWithNavController(navController)
    }
}