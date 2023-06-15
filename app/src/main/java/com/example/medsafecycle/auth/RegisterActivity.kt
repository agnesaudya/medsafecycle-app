package com.example.medsafecycle.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.example.medsafecycle.AuthResponse
import com.example.medsafecycle.R
import com.example.medsafecycle.databinding.ActivityRegisterBinding
import com.example.medsafecycle.viewmodel.auth.RegisterViewModel

class RegisterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        registerViewModel.registerResponse.observe(this) {
            showResult(it)
        }

        binding.button.setOnClickListener {
            val email = binding.myEmailText.text.toString()
            val pwd = binding.myPwdText.text.toString()
            val name = binding.myNameText.text.toString()
            val address = binding.myAddressText.text.toString()

            // Type 0 = Hospital, current registration only support hospital user
            registerViewModel.register(email,name,pwd,address,0)


            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.root.windowToken, 0)

            val moveIntent = Intent(this, LoginActivity::class.java)
            startActivity(moveIntent)
            finish()

        }

        binding.moveLogin.setOnClickListener {
            val moveIntent = Intent(this, LoginActivity::class.java)
            startActivity(moveIntent)
            finish()
        }


    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showResult(res: AuthResponse) {
        if(res.status=="client error"){
            Toast.makeText(this, res.message, Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, res.message, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }
}