package com.example.medsafecycle.database

import androidx.lifecycle.LiveData
import androidx.room.*

interface GuestLimbahDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(guestLimbah: GuestLimbah)
    @Update
    fun update(guestLimbah: GuestLimbah)
    @Delete
    fun delete(guestLimbah: GuestLimbah)
    @Query("SELECT * from guestlimbah")
    fun getAllLimbah(): LiveData<List<GuestLimbah>>
    @Query("SELECT * FROM guestlimbah WHERE id = :id")
    fun getLimbahById(id: Int): LiveData<GuestLimbah>
}