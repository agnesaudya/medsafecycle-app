package com.example.medsafecycle.viewmodel.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medsafecycle.AuthResponse
import com.example.medsafecycle.config.ApiConfig
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel()  {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _registerResponse = MutableLiveData<AuthResponse>()
    val registerResponse: LiveData<AuthResponse> = _registerResponse

    companion object{
        private const val TAG = "RegisterViewModel"
    }
    fun register(email: String, name:String, password:String, address:String, type:Long) {
        _isLoading.value = true
        val client = ApiConfig.getApiServiceNoToken().register(email,name,password, address, 0)
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
                        _registerResponse.value = responseBody!!

                    }
                } else {

                    val gson = Gson()
                    val errorResponse: AuthResponse = gson.fromJson(response.errorBody()?.string(), AuthResponse::class.java)
                    _registerResponse.value = AuthResponse(data=null,message = errorResponse.message, status = errorResponse.status)


                }
            }
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}