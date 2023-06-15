package com.example.medsafecycle.limbah

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.medsafecycle.HistoryResponseItem
import com.example.medsafecycle.databinding.HistoryItemBinding



class HospitalHistoryAdapter(private val listLimbah: List<HistoryResponseItem>) : RecyclerView.Adapter<HospitalHistoryAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(limbah: HistoryResponseItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val limbah = listLimbah[position]
        holder.itemView.setOnClickListener {
            if (limbah != null) {
                onItemClickCallback.onItemClicked(limbah)
            }
        }
        if (limbah != null) {
            holder.bind(limbah)
        }
    }

    class ViewHolder(private val binding: HistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HistoryResponseItem) {

            binding.tanggalUpload.text = data.createdAt
            binding.jenisLimbah.text = data.wasteType

            Glide.with(itemView.context)
                .load(data.imageLink)
                .into(binding.fotoLimbah)


        }


    }
    override fun getItemCount(): Int = listLimbah.size
}