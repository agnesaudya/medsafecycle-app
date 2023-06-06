package com.example.medsafecycle.home.guest

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medsafecycle.R
import com.example.medsafecycle.limbah.LimbahDummy

// TODO : Modifikasi sesuai API yaa

class HistoryLimbahAdapter(private val listLimbah: ArrayList<LimbahDummy>) : RecyclerView.Adapter<HistoryLimbahAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvJenis: TextView = itemView.findViewById(R.id.jenis_limbah)
        val tvTanggal: TextView = itemView.findViewById(R.id.tanggal_upload)
        val tvImage: ImageView = itemView.findViewById(R.id.foto_limbah)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listLimbah.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (jenis,tanggal) = listLimbah[position]
        holder.tvTanggal.text = tanggal
        holder.tvJenis.text = jenis

        // TODO : Di sini blom ditambahin buat imageViewnya
        // holder.tvImage

        holder.itemView.setOnClickListener {

            // TODO : Kalo mau get lagi dari API, ganti lagi aja yaa cara kerjanya

            val intentDetail = Intent(holder.itemView.context, DetailLimbahActivity::class.java)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

}