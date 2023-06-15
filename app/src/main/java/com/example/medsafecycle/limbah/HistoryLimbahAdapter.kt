package com.example.medsafecycle.limbah

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.medsafecycle.HistoryResponseItem
import com.example.medsafecycle.R
import com.example.medsafecycle.databinding.HistoryItemBinding
import com.example.medsafecycle.limbah.HistoryLimbahAdapter.ViewHolder.Companion.DIFF_CALLBACK

// TODO : Modifikasi sesuai API yaa

class HistoryLimbahAdapter: PagingDataAdapter<HistoryResponseItem, HistoryLimbahAdapter.ViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(limbah: HistoryResponseItem)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = getItem(position)
        holder.itemView.setOnClickListener{
            if (data != null) {
                onItemClickCallback.onItemClicked(data)
            }
        }
        if (data != null) {
            holder.bind(data)
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

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryResponseItem>() {
            override fun areItemsTheSame(oldItem: HistoryResponseItem, newItem: HistoryResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: HistoryResponseItem, newItem: HistoryResponseItem): Boolean {
                return oldItem.wasteId == newItem.wasteId
            }
        }
    }
}


}