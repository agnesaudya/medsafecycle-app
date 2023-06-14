package com.example.medsafecycle.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.medsafecycle.HistoryResponseItem
import com.example.medsafecycle.database.LimbahRepository

class HistoryViewModel(limbahRepository: LimbahRepository): ViewModel()  {

    val history: LiveData<PagingData<HistoryResponseItem>> =
        limbahRepository.getHistory().cachedIn(viewModelScope)


}

