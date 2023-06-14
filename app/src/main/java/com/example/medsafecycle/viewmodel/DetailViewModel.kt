package com.example.medsafecycle.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medsafecycle.LimbahResponse
import com.example.medsafecycle.config.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _limbahResponse = MutableLiveData<LimbahResponse>()
    val limbahResponse: LiveData<LimbahResponse> = _limbahResponse

    companion object{
        private const val TAG = "DetailViewModel"
    }
    fun getLimbah(limbah_id: String, token:String) {
        val client = ApiConfig.getApiServiceWithToken(token).getLimbah(limbah_id)
        _isLoading.value = true


        client.enqueue(object : Callback<LimbahResponse> {
            override fun onResponse(
                call: Call<LimbahResponse>,
                response: Response<LimbahResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _limbahResponse.value = responseBody!!

                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    Log.e(TAG, "onFailure: ${response.errorBody()}")
//                    val gson = Gson()
//                    gson.fromJson(response.errorBody()?.string(), ProfileResponse::class.java)
                    _limbahResponse.value = LimbahResponse(wasteInformation = null, wasteType = null, wasteTypeId = null, imageLink = null)

                }
            }
            override fun onFailure(call: Call<LimbahResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}