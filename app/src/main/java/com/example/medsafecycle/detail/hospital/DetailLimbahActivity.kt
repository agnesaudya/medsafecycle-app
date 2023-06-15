package com.example.medsafecycle.detail.hospital

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.provider.FontsContractCompat.Columns.RESULT_CODE
import com.bumptech.glide.Glide
import com.example.medsafecycle.LimbahResponse

import com.example.medsafecycle.R
import com.example.medsafecycle.UserPreference
import com.example.medsafecycle.viewmodel.DetailViewModel

class DetailLimbahActivity : AppCompatActivity() {

    private lateinit var jenisLimbah:TextView
    private lateinit var cara_pembuangan:TextView
    private lateinit var deskripsi_limbah:TextView
    private lateinit var delete:CardView
    private lateinit var mUserPreference: UserPreference
    private lateinit var progressBar:ProgressBar
    private lateinit var image:ImageView
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
        image = findViewById(R.id.limbah_photo_detail)
        progressBar = findViewById(R.id.progressBar)
        deskripsi_limbah = findViewById(R.id.deskripsi_limbah)



        val waste_id = intent.getLongExtra("waste_id",0)

        detailViewModel.getLimbah(waste_id,mUserPreference.getToken().toString())

        detailViewModel.deleteRes.observe(this) {
            Toast.makeText(this, "${it}", Toast.LENGTH_SHORT).show()
            if(it=="data berhasil dihapus"){
                val resultIntent = Intent()
                setResult(1, resultIntent)
                finish()
            }
        }
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.limbahResponse.observe(this) {limbah->
            showResult(limbah)
        }

        delete.setOnClickListener{
            detailViewModel.delete(waste_id, mUserPreference.getToken().toString())
        }


        manageToolbar()

    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility  = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showResult(res: LimbahResponse) {

        jenisLimbah.text=res.wasteType
        deskripsi_limbah.text= res.wasteInformation?.description ?: ""
        val bulletList = StringBuilder()
        for (item in res.wasteInformation?.extermination!!){
            bulletList.append("\u2022 $item\n")
        }

        cara_pembuangan.text=bulletList.toString()
        Glide.with(this)
            .load(res.imageLink)
            .into(image)


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