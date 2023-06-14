package com.example.medsafecycle.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.medsafecycle.database.GuestLimbah

class GuestLimbahDiffCallback (private val oldGuestLimbahList: List<GuestLimbah>, private val newGuestLimbahList: List<GuestLimbah>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldGuestLimbahList.size
    override fun getNewListSize(): Int = newGuestLimbahList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldGuestLimbahList[oldItemPosition].id == newGuestLimbahList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldGuestLimbah =oldGuestLimbahList[oldItemPosition]
        val newGuestLimbah = newGuestLimbahList[newItemPosition]
        return oldGuestLimbah.description== newGuestLimbah.description
    }
}