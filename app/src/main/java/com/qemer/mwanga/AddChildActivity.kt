package com.qemer.mwanga.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.qemer.mwanga.R
import com.qemer.mwanga.databinding.ActivityAddchildBinding

class AddChildActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddchildBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityAddchildBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}