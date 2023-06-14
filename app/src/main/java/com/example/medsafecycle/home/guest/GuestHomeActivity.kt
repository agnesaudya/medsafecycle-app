package com.example.medsafecycle.home.guest

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medsafecycle.R
import com.example.medsafecycle.auth.RegisterActivity
import com.example.medsafecycle.createCustomTempFile
import com.example.medsafecycle.database.GuestLimbah
import com.example.medsafecycle.home.popup.UploadPopup
import com.example.medsafecycle.limbah.*
import com.example.medsafecycle.uriToFile
import java.io.File
import com.example.medsafecycle.viewmodel.GuestDetailViewModel
import com.example.medsafecycle.viewmodel.GuestHomeViewModel
import com.example.medsafecycle.viewmodel.GuestViewModelFactory

//  TODO : Buat guest, pake shared preference aja ya :) soalnya dia gapunya akun, tapi perlu history
class GuestHomeActivity : AppCompatActivity() {
    private lateinit var cameraButton: CardView
    private lateinit var rvLimbah: RecyclerView
    private lateinit var guestHomeViewModel: GuestHomeViewModel
    private lateinit var textRedirect: CardView
    private lateinit var registerRedirect: CardView
    private lateinit var adapter: GuestHistoryLimbahAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)



        supportActionBar?.hide()

        guestHomeViewModel = obtainViewModel(this)
        setUpButton()
        setUpRecyclerView()

        textRedirect = findViewById(R.id.redirect_nearest_hospital)
        cameraButton = findViewById(R.id.camera_button)


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

        // TODO : Button gua pake buat redirectNotFound, ganti ae
        cameraButton.setOnClickListener {
            val uploadPopup = UploadPopup()
            uploadPopup.show(supportFragmentManager,"upload_popup")
        }

    }


    private fun setUpRecyclerView(){
        rvLimbah = findViewById(R.id.recyclerView)
        rvLimbah.setHasFixedSize(true)

//        list.addAll(getListDummy())
        showRecyclerList()
    }

    private fun setUpButton(){
        val toHistory: TextView = findViewById(R.id.to_history_button)
        toHistory.setOnClickListener {
            val moveIntent = Intent(this@GuestHomeActivity, HistoryLimbahActivity::class.java)
            startActivity(moveIntent)
        }
    }




    // TODO : Jangan lupa sesuain ini sama output API, terutama bagian pas nambahin list
    private fun showRecyclerList() {
        rvLimbah.layoutManager = LinearLayoutManager(this)
        guestHomeViewModel.getAllLimbah().observe(this){
            limbahList ->
            if (limbahList  != null) {
                adapter.setListLimbah(limbahList)
            }
        }

        adapter = GuestHistoryLimbahAdapter()

        rvLimbah.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): GuestHomeViewModel {
        val factory = GuestViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(GuestHomeViewModel::class.java)
    }

//     TODO : Ini buat bikin dummy. Nanti hapus ini dan di values.strings.xml

//    private val list = ArrayList<GuestLimbah>()
//    private fun getListDummy(): ArrayList<LimbahDummy> {
//
//        val dataJenis = resources.getStringArray(R.array.data_jenis)
//        val dataTanggal = resources.getStringArray(R.array.data_tanggal)
//
//        val listDummy = ArrayList<LimbahDummy>()
//        for (i in dataJenis.indices) {
//            val dummy = LimbahDummy(dataJenis[i],dataTanggal[i])
//            listDummy.add(dummy)
//        }
//
//        // TODO : Di halaman ini cuma perlu max 3, nanti sesuain yaa
//        if(listDummy.size > 3){
//            return ArrayList(listDummy.subList(0, 3))
//        } else {
//            return listDummy
//        }
//    }

}