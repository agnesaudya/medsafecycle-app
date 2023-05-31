package com.example.medsafecycle.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.medsafecycle.AuthResponse
import com.example.medsafecycle.R
import com.example.medsafecycle.TokenPreference
import com.example.medsafecycle.databinding.ActivityLoginBinding
import com.example.medsafecycle.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var mUserPreference: TokenPreference
    private var isPreferenceEmpty = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    private fun showResult(res: AuthResponse) {
        if(res.status=="client error"){
            Toast.makeText(this, res.message, Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, res.message, Toast.LENGTH_SHORT).show()
//            val moveIntent = Intent(this@LoginActivity, AllStoriesActivity::class.java)
//            startActivity(moveIntent)
//            finish()
        }
    }

    private fun saveToken(token:String) {
        val tokenPreference = TokenPreference(this)
        tokenPreference.setToken(token)
        Toast.makeText(this, "User saved", Toast.LENGTH_SHORT).show()
    }
}

//private fun showLoading(isLoading: Boolean) {
//    binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//}

