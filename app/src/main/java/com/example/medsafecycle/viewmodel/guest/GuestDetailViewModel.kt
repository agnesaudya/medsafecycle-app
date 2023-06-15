package com.example.medsafecycle.viewmodel.guest

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.medsafecycle.database.GuestLimbah
import com.example.medsafecycle.database.GuestLimbahRepository


class GuestDetailViewModel(application: Application): ViewModel() {
    private val limbahRepository: GuestLimbahRepository = GuestLimbahRepository(application)

    fun delete(limbah: GuestLimbah) {
        limbahRepository.delete(limbah)
    }

    fun getLimbahById(id: Long): LiveData<GuestLimbah> =limbahRepository.getLimbahById(id)

}