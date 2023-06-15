package com.example.medsafecycle.limbah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.medsafecycle.LimbahResponse
import com.example.medsafecycle.ProfileResponse

import com.example.medsafecycle.R
import com.example.medsafecycle.UserPreference
import com.example.medsafecycle.landing.LandingActivity
import com.example.medsafecycle.viewmodel.DetailViewModel
import com.example.medsafecycle.viewmodel.GuestDetailViewModel

class DetailLimbahActivity : AppCompatActivity() {

    private lateinit var jenisLimbah:TextView
    private lateinit var cara_pembuangan:TextView
    private lateinit var deskripsi_limbah:TextView
    private lateinit var delete:CardView
    private lateinit var mUserPreference: UserPreference
    private lateinit var progressBar:ProgressBar
    private val detailViewModel by viewModels<DetailViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_limbah)
        mUserPreference = UserPreference(this)
        val done : CardView = findViewById(R.id.selesai)
        done.setOnClickListener {
            finish()
        }

        jenisLimbah = findViewById(R.id.jenis_limbah)
        cara_pembuangan = findViewById(R.id.cara_pembuangan)
        delete = findViewById(R.id.delete)
        progressBar = findViewById(R.id.progressBar)
        deskripsi_limbah = findViewById(R.id.deskripsi_limbah)
        done.setOnClickListener {
            finish()
        }


        val waste_id = intent.getLongExtra("waste_id",0)

        detailViewModel.getLimbah(waste_id,mUserPreference.getToken().toString())

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.limbahResponse.observe(this) {
            showResult(it)
        }


        manageToolbar()

    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility  = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showResult(res: LimbahResponse) {

        jenisLimbah.text=res.wasteType
        deskripsi_limbah.text= res.wasteInformation?.description ?: ""
//                cara_pembuangan.text=limbah.extermination

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