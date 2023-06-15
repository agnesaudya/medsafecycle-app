package com.example.medsafecycle.viewmodel.guest

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medsafecycle.UploadResponse
import com.example.medsafecycle.config.ApiConfig
import com.example.medsafecycle.database.GuestLimbah
import com.example.medsafecycle.database.GuestLimbahRepository
import com.google.gson.Gson
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopupViewModel(application: Application) : ViewModel(){
    private val limbahRepository: GuestLimbahRepository = GuestLimbahRepository(application)
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _scanResponse = MutableLiveData<UploadResponse>()
    val scanResponse: LiveData<UploadResponse> = _scanResponse

    companion object{
        private const val TAG = "PopupViewModel"
    }

    fun insert(limbah: GuestLimbah) {
        limbahRepository.insert(limbah)
    }
    fun scan(image: MultipartBody.Part) {
        Log.d("test",image.body.toString())
        _isLoading.value = true
        var client =  ApiConfig.getApiServiceNoToken().scanImage(image)


        client.enqueue(object : Callback<UploadResponse> {
            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d(TAG, "onSuccess: ${responseBody.message}")
                        _scanResponse.value = responseBody!!

                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    Log.e(TAG, "onFailure: ${response.errorBody()}")
                    val gson = Gson()
                    val errorResponse: UploadResponse = gson.fromJson(response.errorBody()?.string(), UploadResponse::class.java)
                    Log.d(TAG, errorResponse.message)
                    _scanResponse.value = UploadResponse(message = errorResponse.message, wasteInformation = null)

                }
            }
            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}