package com.qemer.mwanga.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.qemer.mwanga.onboarding.ViewPagerAdapter
import com.qemer.mwanga.onboarding.OnBoardingViewPager
import com.qemer.mwanga.R
import com.qemer.mwanga.auth.LogInActivity
import com.qemer.mwanga.databinding.FragmentOnBoardingViewPagerBinding
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class OnBoardingViewPager : Fragment() {

    private var _binding: FragmentOnBoardingViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOnBoardingViewPagerBinding.inflate(inflater, container, false)
        
         val onBoardingScreens = arrayListOf(
            FirstOnboardingScreenFragment(), SecondOnboardingScreenFragment()
        )

        val adapter = ViewPagerAdapter(
            onBoardingScreens, requireActivity().supportFragmentManager, lifecycle
        )
        val wormDotsIndicator: WormDotsIndicator = binding.wormDotsIndicator
        val onBoardingViewPager: ViewPager2 = binding.viewPager2

        onBoardingViewPager.adapter = adapter
        wormDotsIndicator.attachTo(onBoardingViewPager)

        binding.skipButton.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(this, LogInActivity::class.java))
                finish()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}