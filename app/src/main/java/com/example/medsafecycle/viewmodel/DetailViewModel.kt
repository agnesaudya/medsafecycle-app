package com.example.medsafecycle.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medsafecycle.LimbahResponse
import com.example.medsafecycle.config.ApiConfig
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _limbahResponse = MutableLiveData<LimbahResponse>()
    val limbahResponse: LiveData<LimbahResponse> = _limbahResponse
    private val _deleteRes= MutableLiveData<String>()
    val deleteRes: LiveData<String> = _deleteRes


    companion object{
        private const val TAG = "DetailViewModel"
    }
    fun getLimbah(limbah_id: Long, token:String) {
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
                    Log.e(TAG, "onFailure: ${response.errorBody()?.string()}")
                    _limbahResponse.value = LimbahResponse(wasteInformation = null, wasteType = null, wasteTypeId = null, imageLink = null)

                }
            }
            override fun onFailure(call: Call<LimbahResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun delete(limbah_id: Long, token:String){
        val client = ApiConfig.getApiServiceWithToken(token).deleteLimbah(limbah_id)
        _isLoading.value = true


        client.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d(TAG, "onSuccess: ${response.code()}")
                        _deleteRes.value = responseBody.string()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    Log.e(TAG, "onFailure: ${response.errorBody()?.string()}")


                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })

    }
}