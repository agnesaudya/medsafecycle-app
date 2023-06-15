package com.example.medsafecycle.detail.guest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.medsafecycle.R
import com.example.medsafecycle.viewmodel.GuestDetailViewModel
import com.example.medsafecycle.viewmodel.GuestViewModelFactory
import java.io.File

class GuestDetailActivity : AppCompatActivity() {
    private lateinit var guestDetailViewModel: GuestDetailViewModel
    private lateinit var jenisLimbah:TextView
    private lateinit var cara_pembuangan:TextView
    private lateinit var deskripsi_limbah:TextView
    private lateinit var delete:CardView
    private lateinit var image: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_detail)

        guestDetailViewModel = obtainViewModel(this@GuestDetailActivity)

        val done : CardView = findViewById(R.id.selesai)
        jenisLimbah = findViewById(R.id.jenis_limbah)
        image = findViewById(R.id.limbah_photo_detail)
        cara_pembuangan = findViewById(R.id.cara_pembuangan)
        delete = findViewById(R.id.delete)
        deskripsi_limbah = findViewById(R.id.deskripsi_limbah)
        done.setOnClickListener {
            finish()
        }


        val waste_id = intent.getLongExtra("waste_id",0)

        guestDetailViewModel.getLimbahById(waste_id).observe(this){
                limbah ->
            if (limbah  != null) {
                jenisLimbah.text=limbah.name
                deskripsi_limbah.text=limbah.description
                val bulletList = StringBuilder()
                for (item in limbah.extermination){
                    bulletList.append("\u2022 $item\n")
                }

                cara_pembuangan.text=bulletList.toString()
                Glide.with(this)
                    .load(File(limbah.imagePath))
                    .into(image)

                delete.setOnClickListener{
                    guestDetailViewModel.delete(limbah)
                    finish()
                }
            }
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

    private fun obtainViewModel(activity: AppCompatActivity): GuestDetailViewModel {
        val factory = GuestViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(GuestDetailViewModel::class.java)
    }
}