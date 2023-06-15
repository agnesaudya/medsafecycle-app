package com.example.medsafecycle.viewmodel.guest

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.medsafecycle.database.GuestLimbah
import com.example.medsafecycle.database.GuestLimbahRepository

class GuestHomeViewModel(application: Application) : ViewModel() {
    fun getAllLimbah(): LiveData<List<GuestLimbah>> = guestLimbahRepository.getNewestLimbah(3)
    private val guestLimbahRepository: GuestLimbahRepository = GuestLimbahRepository(application)

}