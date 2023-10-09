package com.qemer.mwanga.dashboard.addchild

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.qemer.mwanga.databinding.ActivityAddChildBinding


class AddChildActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddChildBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityAddChildBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val parentName = intent.getStringExtra("id")
        Log.d("Gideon", parentName.toString())
    }
}