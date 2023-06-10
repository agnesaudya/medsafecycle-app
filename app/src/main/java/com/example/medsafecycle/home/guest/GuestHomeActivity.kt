package com.example.medsafecycle.home.guest

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medsafecycle.R
import com.example.medsafecycle.auth.LoginActivity
import com.example.medsafecycle.auth.RegisterActivity
import com.example.medsafecycle.landing.LandingActivity
import com.example.medsafecycle.limbah.HistoryLimbahActivity
import com.example.medsafecycle.limbah.HistoryLimbahAdapter
import com.example.medsafecycle.limbah.LimbahDummy

//  TODO : Buat guest, pake shared preference aja ya :) soalnya dia gapunya akun, tapi perlu history
class GuestHomeActivity : AppCompatActivity() {

    private lateinit var rvLimbah: RecyclerView
    private lateinit var textRedirect: CardView
    private lateinit var registerRedirect: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()

        setUpButton()
        setUpRecyclerView()

        textRedirect = findViewById(R.id.redirect_nearest_hospital)
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
    }

    private fun setUpRecyclerView(){
        rvLimbah = findViewById(R.id.recyclerView)
        rvLimbah.setHasFixedSize(true)

        list.addAll(getListDummy())
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
        val listAdapter = HistoryLimbahAdapter(list)
        rvLimbah.adapter = listAdapter
    }

    // TODO : Ini buat bikin dummy. Nanti hapus ini dan di values.strings.xml

    private val list = ArrayList<LimbahDummy>()
    private fun getListDummy(): ArrayList<LimbahDummy> {

        val dataJenis = resources.getStringArray(R.array.data_jenis)
        val dataTanggal = resources.getStringArray(R.array.data_tanggal)

        val listDummy = ArrayList<LimbahDummy>()
        for (i in dataJenis.indices) {
            val dummy = LimbahDummy(dataJenis[i],dataTanggal[i])
            listDummy.add(dummy)
        }

        // TODO : Di halaman ini cuma perlu max 3, nanti sesuain yaa
        if(listDummy.size > 3){
            return ArrayList(listDummy.subList(0, 3))
        } else {
            return listDummy
        }
    }

}