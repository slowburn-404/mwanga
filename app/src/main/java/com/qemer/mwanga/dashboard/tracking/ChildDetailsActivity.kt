package com.qemer.mwanga.dashboard.tracking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qemer.mwanga.R
import com.qemer.mwanga.databinding.ActivityChildDetailsBinding

class ChildDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChildDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChildDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trackingFab.setOnClickListener {
            val intent = Intent(this, TrackingActivity1::class.java)
            startActivity(intent)
        }
        binding.tracingTopAppBar.setNavigationOnClickListener {
            finish()
        }
    }
}