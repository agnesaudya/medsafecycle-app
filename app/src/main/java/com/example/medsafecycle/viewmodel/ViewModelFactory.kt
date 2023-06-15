package com.example.medsafecycle.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.medsafecycle.viewmodel.guest.GuestDetailViewModel
import com.example.medsafecycle.viewmodel.guest.GuestHomeViewModel
import com.example.medsafecycle.viewmodel.guest.GuestLimbahHistoryViewModel
import com.example.medsafecycle.viewmodel.guest.PopupViewModel

class AuthViewModelFactory(private val context: Context) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class GuestViewModelFactory(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {


    companion object {
        @Volatile
        private var INSTANCE: GuestViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): GuestViewModelFactory {
            if (INSTANCE == null) {
                synchronized(GuestViewModelFactory::class.java) {
                    INSTANCE = GuestViewModelFactory(application)
                }
            }
            return INSTANCE as GuestViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GuestDetailViewModel::class.java)) {
            return GuestDetailViewModel(mApplication) as T
        }else if(modelClass.isAssignableFrom(GuestHomeViewModel::class.java)) {
            return GuestHomeViewModel(mApplication) as T
        }else if(modelClass.isAssignableFrom(PopupViewModel::class.java)) {
            return PopupViewModel(mApplication) as T
        }else if(modelClass.isAssignableFrom(GuestLimbahHistoryViewModel::class.java)) {
            return GuestLimbahHistoryViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}