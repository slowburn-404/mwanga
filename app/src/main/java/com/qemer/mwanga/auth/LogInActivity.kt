package com.qemer.mwanga.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.view.WindowCompat
import com.qemer.mwanga.R
import com.qemer.mwanga.dashboard.MainDashboardActivity
import com.qemer.mwanga.dashboard.home.HomeFragment
import com.qemer.mwanga.databinding.ActivityLogInBinding
import com.qemer.mwanga.databinding.ActivityMainDashboardBinding
import com.qemer.mwanga.models.LoginRequest
import com.qemer.mwanga.models.LoginResponse
import com.qemer.mwanga.repository.UserRepository
import com.qemer.mwanga.viewmodel.UserViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding

    private lateinit var loginRequest: LoginRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onResume()

//        binding.btLogin.setOnClickListener{
//
//            val  intent = Intent(this, MainDashboardActivity::class.java)
//            startActivity(intent)
//            finish()
//            validateLogin()
//        }
    }

    override fun onResume() {
        super.onResume()
        binding.btLogin.setOnClickListener {
            validateLogin()
            // Move the intent code here if you want to navigate after validation.
            val intent = Intent(this@LogInActivity, MainDashboardActivity::class.java)
            startActivity(intent)
        }

    }


    fun clearErrorOnType() {
        binding.tilName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.tilName.error = null
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tilName.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.tilPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tilPassword.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }


    private fun validateLogin() {
        val phoneNumber = binding.nameLogIn.editText?.text.toString()
        val password = binding.passwordLogIn.editText?.text.toString()

        if (phoneNumber.isEmpty()) {
            binding.tilName.error = "Phone number is required"
            return  // Return early if there's an error
        }

        if (password.isEmpty()) {
            binding.tilPassword.error = "Password is required"
            return  // Return early if there's an error
        }

        // Continue with login process if no errors
        loginRequest = LoginRequest(
            phoneNumber = phoneNumber,
            password = password
        )
    }

}