package com.example.medsafecycle.database

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class GuestLimbahRepository(application: Application) {
    private val guestLimbahDao: GuestLimbahDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = GuestLimbahDatabase.getDatabase(application)
        guestLimbahDao = db.favoriteUserDao()
    }
    fun getAllLimbah(): LiveData<List<GuestLimbah>> = guestLimbahDao.getAllLimbah()

    fun insert(guestLimbah: GuestLimbah) {
        executorService.execute { guestLimbahDao.insert(guestLimbah) }
    }
    fun delete(guestLimbah: GuestLimbah) {
        executorService.execute { guestLimbahDao.delete(guestLimbah) }
    }

    fun getLimbahById(id: Long): LiveData<GuestLimbah> = guestLimbahDao.getLimbahById(id)
}