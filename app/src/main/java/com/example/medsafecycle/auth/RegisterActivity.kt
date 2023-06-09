package com.example.medsafecycle.auth

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import com.example.medsafecycle.AuthResponse
import com.example.medsafecycle.R
import com.example.medsafecycle.databinding.ActivityLoginBinding
import com.example.medsafecycle.databinding.ActivityRegisterBinding
import com.example.medsafecycle.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.typeSpinner.onItemSelectedListener

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
            val type = binding.typeSpinner.selectedItem.toString()
            if(type == "Hospital"){
                registerViewModel.register(email,name,pwd,address,0)
            }else{
                registerViewModel.register(email,name,pwd,address,1)
            }

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.root.windowToken, 0)

        }


// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.type_user,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.typeSpinner.adapter = adapter
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
        // An item was selected. You can retrieve the selected item using
//        parent.getItemAtPosition(pos)
        // parent.getItemAtPosition(pos)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}