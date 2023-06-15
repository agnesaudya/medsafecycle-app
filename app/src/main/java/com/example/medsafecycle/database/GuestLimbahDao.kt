package com.example.medsafecycle.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GuestLimbahDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(guestLimbah: GuestLimbah)
    @Update
    fun update(guestLimbah: GuestLimbah)
    @Delete
    fun delete(guestLimbah: GuestLimbah)
    @Query("SELECT * from GuestLimbah")
    fun getAllLimbah(): LiveData<List<GuestLimbah>>
    @Query("SELECT * FROM GuestLimbah WHERE id = :id")
    fun getLimbahById(id: Long): LiveData<GuestLimbah>
    @Query("SELECT * FROM GuestLimbah ORDER BY id DESC LIMIT :total")
    fun getNewestLimbah(total:Int): LiveData<List<GuestLimbah>>
}