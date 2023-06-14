package com.example.medsafecycle.viewmodel

import android.content.Context
import com.example.medsafecycle.UserPreference
import com.example.medsafecycle.config.ApiConfig
import com.example.medsafecycle.database.LimbahDatabase
import com.example.medsafecycle.database.LimbahRepository

object Injection {
    fun provideRepository(context: Context): LimbahRepository {
        val database = LimbahDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiServiceNoToken()
        val userPreference = UserPreference(context)
        return LimbahRepository(database, apiService, "${userPreference.getToken()}")
    }
}