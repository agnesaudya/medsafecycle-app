package com.example.medsafecycle.home.guest

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medsafecycle.R
import com.example.medsafecycle.auth.RegisterActivity
import com.example.medsafecycle.home.popup.UploadPopup
import com.example.medsafecycle.limbah.*
import com.example.medsafecycle.viewmodel.guest.GuestHomeViewModel
import com.example.medsafecycle.viewmodel.GuestViewModelFactory


class GuestHomeActivity : AppCompatActivity() {
    private lateinit var cameraButton: CardView
    private lateinit var rvLimbah: RecyclerView
    private lateinit var guestHomeViewModel: GuestHomeViewModel
    private lateinit var textRedirect: CardView
    private lateinit var registerRedirect: CardView
    private lateinit var adapter: GuestHomeHistoryAdapter
    private lateinit var notFound: TextView
    private lateinit var toHistory: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        supportActionBar?.hide()

        guestHomeViewModel = obtainViewModel(this)
        setUpButton()

        textRedirect = findViewById(R.id.redirect_nearest_hospital)
        cameraButton = findViewById(R.id.camera_button)
        notFound = findViewById(R.id.not_found)
        toHistory = findViewById(R.id.to_history_button)

        guestHomeViewModel.getAllLimbah().observe(this){
                limbahList ->
            if (limbahList  != null) {
                adapter.setListLimbah(limbahList)
                adapter.notifyDataSetChanged()

                if(limbahList.isEmpty()){
                    notFound.visibility = View.VISIBLE
                    toHistory.visibility = View.INVISIBLE

                } else{
                    notFound.visibility = View.GONE
                    toHistory.visibility = View.VISIBLE
                }

            }
        }

        rvLimbah = findViewById(R.id.recyclerView)

        adapter = GuestHomeHistoryAdapter()
        rvLimbah.layoutManager = LinearLayoutManager(this)
        rvLimbah.setHasFixedSize(true)
        rvLimbah.adapter = adapter


        textRedirect.setOnClickListener {

            val query = "Rumah Sakit Terdekat"
            val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(query)}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

            mapIntent.setPackage("com.google.android.apps.maps")
            mapIntent.resolveActivity(packageManager)?.let {
                startActivity(mapIntent)
            }

        }

        registerRedirect = findViewById(R.id.redirect_register)
        registerRedirect.setOnClickListener {
            val i = Intent(this@GuestHomeActivity, RegisterActivity::class.java)
            startActivity(i)
            finish()
        }

        cameraButton.setOnClickListener {
            var uploadPopup = UploadPopup()
            uploadPopup.show(supportFragmentManager,"upload_popup")

        }

    }


    private fun setUpButton(){
        val toHistory: TextView = findViewById(R.id.to_history_button)
        toHistory.setOnClickListener {
            val moveIntent = Intent(this@GuestHomeActivity, GuestLimbahHistoryActivity::class.java)
            startActivity(moveIntent)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): GuestHomeViewModel {
        val factory = GuestViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[GuestHomeViewModel::class.java]
    }



}