package com.example.medsafecycle.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medsafecycle.AuthResponse
import com.example.medsafecycle.config.ApiConfig
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _loginResponse = MutableLiveData<AuthResponse>()
    val loginResponse: LiveData<AuthResponse> = _loginResponse

    companion object{
        private const val TAG = "LoginViewModel"
    }
    fun login(email: String, password:String) {
        _isLoading.value = true
        val client = ApiConfig.getApiServiceNoToken().login(email,password)
        client.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(
                call: Call<AuthResponse>,
                response: Response<AuthResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d(TAG, "onSuccess: ${responseBody.message}")
                        _loginResponse.value = responseBody!!

                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    Log.e(TAG, "onFailure: ${response.errorBody()}")
                    val gson = Gson()
                    val errorResponse: AuthResponse = gson.fromJson(response.errorBody()?.string(), AuthResponse::class.java)
                    _loginResponse.value = AuthResponse(data=null, message = errorResponse.message, status = errorResponse.status)

                }
            }
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}