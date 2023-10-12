package com.qemer.mwanga.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.qemer.mwanga.R
import com.qemer.mwanga.databinding.ActivityMainDashboardBinding

class MainDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  =  ActivityMainDashboardBinding.inflate(layoutInflater)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(binding.root)

        binding.dashboardBottomNavigation.itemIconTintList = null

         val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_dashboard_navhost_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.dashboardBottomNavigation.setupWithNavController(navController)

    }
}