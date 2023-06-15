package com.example.medsafecycle.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medsafecycle.HistoryResponseItem
import com.example.medsafecycle.config.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HospitalHomeViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _historyResponse = MutableLiveData<List<HistoryResponseItem>>()
    val historyResponse: LiveData<List<HistoryResponseItem>> = _historyResponse

    companion object{
        private const val TAG = "HospitalHomeViewModel"
    }


    fun getFixedHistory(token: String) {
        val client = ApiConfig.getApiServiceWithToken(token).getFixedLimbah(offset = 0, size = 3)
        _isLoading.value = true

        client.enqueue(object : Callback<List<HistoryResponseItem>> {
            override fun onResponse(
                call: Call<List<HistoryResponseItem>>,
                response: Response<List<HistoryResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _historyResponse.value = responseBody!!

                    }
                } else {

                    Log.e(TAG, "onFailure: ${response.message()}")
                    Log.e(TAG, "onFailure: ${response.errorBody()?.string()}")

                    _historyResponse.value = emptyList()
                }
            }
            override fun onFailure(call: Call<List<HistoryResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}