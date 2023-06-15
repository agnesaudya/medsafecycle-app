package com.example.medsafecycle.limbah

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.medsafecycle.R
import com.example.medsafecycle.database.GuestLimbah
import com.example.medsafecycle.detail.guest.GuestDetailActivity
import com.example.medsafecycle.helper.GuestLimbahDiffCallback


class GuestHistoryLimbahAdapter : RecyclerView.Adapter<GuestHistoryLimbahAdapter.ListViewHolder>() {
    private val listLimbah= ArrayList<GuestLimbah>()
        fun setListLimbah(listGuestLimbah: List<GuestLimbah>) {
            val diffCallback = GuestLimbahDiffCallback(this.listLimbah, listLimbah)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            this.listLimbah.clear()
            this.listLimbah.addAll(listGuestLimbah)
            diffResult.dispatchUpdatesTo(this)
        }
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvJenis: TextView = itemView.findViewById(R.id.jenis_limbah)
        val tvTanggal: TextView = itemView.findViewById(R.id.tanggal_upload)
        val tvImage: ImageView = itemView.findViewById(R.id.foto_limbah)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.resized_history_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listLimbah.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val limbah = listLimbah[position]
        holder.tvTanggal.text = limbah.createdAt
        holder.tvJenis.text = limbah.name
        Glide.with(holder.itemView)
            .load(limbah.imagePath)
            .into(holder.tvImage)

        // TODO : Di sini blom ditambahin buat imageViewnya


        holder.itemView.setOnClickListener {

            // TODO : Kalo mau get lagi dari API, ganti lagi aja yaa cara kerjanya

            val intentDetail = Intent(holder.itemView.context, GuestDetailActivity::class.java)
            intentDetail.putExtra("waste_id", limbah.id) // Example: Passing limbah id
            holder.itemView.context.startActivity(intentDetail)
        }
    }

}