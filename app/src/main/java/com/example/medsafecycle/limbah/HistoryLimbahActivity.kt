package com.example.medsafecycle.limbah

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medsafecycle.R

class HistoryLimbahActivity : AppCompatActivity() {

    private lateinit var rvLimbah: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_limbah)

        supportActionBar?.hide()

        //manageToolBar() -> gatau kena error mulu wkwk. nanti gua cek

        rvLimbah = findViewById(R.id.rec_view_history)
        rvLimbah.setHasFixedSize(true)

        list.addAll(getListDummy())
        showRecyclerList()

    }

    private fun manageToolBar(){

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.back_icon)
            title = "Custom Title"
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

        return listDummy

    }
}