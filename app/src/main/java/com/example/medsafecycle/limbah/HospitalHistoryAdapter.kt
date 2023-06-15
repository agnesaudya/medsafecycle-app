package com.example.medsafecycle.limbah

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.medsafecycle.HistoryResponseItem
import com.example.medsafecycle.R

class HospitalHistoryAdapter(private val listLimbah: List<HistoryResponseItem>) : RecyclerView.Adapter<HospitalHistoryAdapter.ListViewHolder>() {

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
        val limbah = listLimbah[position]
        holder.tvTanggal.text = limbah.wasteType
        holder.tvJenis.text = limbah.createdAt
        Glide.with(holder.itemView)
            .load(limbah.imageLink)
            .into(holder.tvImage)


        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailLimbahActivity::class.java)
            intentDetail.putExtra("waste_id",limbah.wasteId)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

}