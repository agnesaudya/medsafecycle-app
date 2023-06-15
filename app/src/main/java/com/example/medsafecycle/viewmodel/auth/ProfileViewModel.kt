package com.example.medsafecycle.viewmodel.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medsafecycle.ProfileResponse
import com.example.medsafecycle.config.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel: ViewModel(){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _profileResponse = MutableLiveData<ProfileResponse>()
    val profileResponse: LiveData<ProfileResponse> = _profileResponse

    companion object{
        private const val TAG = "ProfileViewModel"
    }
    fun getUserinfo(token: String) {
        val client = ApiConfig.getApiServiceWithToken(token).getProfile()
        _isLoading.value = true


        client.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _profileResponse.value = responseBody!!

                    }
                } else {

                    Log.e(TAG, "onFailure: ${response.message()}")
                    Log.e(TAG, "onFailure: ${response.errorBody()}")

                    _profileResponse.value = ProfileResponse(username = null, userAddress = null, userEmail = null)

                }
            }
            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}