package com.example.medsafecycle.limbah

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// TODO : Dummy, kalo udah bisa API-nya, hapus ini

@Parcelize
data class LimbahDummy (
    val jenis: String,
    val tanggal: String
): Parcelable
