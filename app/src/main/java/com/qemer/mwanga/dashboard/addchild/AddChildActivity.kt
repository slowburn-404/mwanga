package com.qemer.mwanga.dashboard.addchild

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.qemer.mwanga.R
import com.qemer.mwanga.databinding.ActivityAddChildBinding

class AddChildActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddChildBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityAddChildBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}