package com.qemer.mwanga.auth

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.material.snackbar.Snackbar
import com.qemer.mwanga.api.ApiLoginClient
import com.qemer.mwanga.dashboard.MainDashboardActivity
import com.qemer.mwanga.databinding.ActivityLogInBinding
import com.qemer.mwanga.models.LoginRequest
import com.qemer.mwanga.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var loginRequest: LoginRequest
    private lateinit var apiClient: ApiLoginClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onResume()
        apiClient = ApiLoginClient()

        binding.btLogin.setOnClickListener {
            if (TextUtils.isEmpty(binding.tilName.text.toString().trim())) {
                binding.tilName.error = "Phone number is required"
            } else if (TextUtils.isEmpty(binding.tilPassword.text.toString().trim())) {
                binding.tilPassword.error = "Password is required"
            } else {
                val progressDialog = ProgressDialog(this)
                progressDialog.setCancelable(false) // set cancelable to false
                progressDialog.setMessage("Logging in...") // set message
                progressDialog.show()

                val loginInfo = LoginRequest(
//                    binding.tilName.text.toString().trim(),
//                    binding.tilPassword.text.toString().trim()
                    "+254713030724",
                    "mwanga12345"
                )
                Log.d("info", loginInfo.toString())
                apiClient.getApiService(this).login(loginInfo).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful) {
                            progressDialog.dismiss()
                            Snackbar.make(it, "Login Successful", Snackbar.LENGTH_SHORT).show()
                            Log.e("Gideon", "onSuccess: ${response.body()}")

                            val token = response.body()!!.token

                            val intent = Intent(this@LogInActivity, MainDashboardActivity::class.java)
                            startActivity(intent)
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        progressDialog.dismiss()
                        Snackbar.make(it, "${t.message}", Snackbar.LENGTH_SHORT).show()
                        Log.e("Gideon", "onFailure: ${t.message}")
                    }
                })
            }
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