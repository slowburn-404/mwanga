package com.qemer.mwanga.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(
    fragments: ArrayList<Fragment>, fm: FragmentManager, lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    private val onBoardingScreens: ArrayList<Fragment> = fragments

    override fun getItemCount(): Int {
        return onBoardingScreens.size
    }

    override fun createFragment(position: Int): Fragment {
        return onBoardingScreens[position]
    }
}