package com.qemer.mwanga.dashboard.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.qemer.mwanga.R
import com.qemer.mwanga.databinding.ActivityRegisterParentBinding

class RegisterParentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterParentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterParentBinding.inflate(layoutInflater)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(binding.root)

    }
}