package com.example.medsafecycle.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager

import android.widget.Toast
import androidx.activity.viewModels
import com.example.medsafecycle.AuthResponse

import com.example.medsafecycle.UserPreference
import com.example.medsafecycle.databinding.ActivityLoginBinding
import com.example.medsafecycle.home.hospital.HospitalHomeActivityBase
import com.example.medsafecycle.viewmodel.auth.LoginViewModel


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var mUserPreference: UserPreference
    private var isPreferenceEmpty = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mUserPreference = UserPreference(this)

        Log.d("d",mUserPreference.getToken().toString())
        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        loginViewModel.loginResponse.observe(this) {
            showResult(it)
        }



        binding.button.setOnClickListener {
            val email = binding.myEmailText.text.toString()
            val pwd = binding.myPwdText.text.toString()
            if ( pwd.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            loginViewModel.login(email,pwd)
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        }
        binding.moveRegister.setOnClickListener {
            val moveIntent = Intent(this, RegisterActivity::class.java)
            startActivity(moveIntent)
            finish()

        }
    }


    private fun showResult(res: AuthResponse) {
        if(res.status=="client error"){
            Toast.makeText(this, res.message, Toast.LENGTH_SHORT).show()

        }else{
            Toast.makeText(this, res.message, Toast.LENGTH_SHORT).show()
            saveToken(res.data?.token.toString())
            val moveIntent = Intent(this@LoginActivity, HospitalHomeActivityBase::class.java)
            startActivity(moveIntent)
            finish()
        }
    }

    private fun saveToken(token:String) {
        val tokenPreference = UserPreference(this)
        tokenPreference.setToken(token)
        Toast.makeText(this, "User login", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}



