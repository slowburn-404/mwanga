package com.qemer.mwanga.dashboard.home

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.qemer.mwanga.Profile
import com.qemer.mwanga.R
import com.qemer.mwanga.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), HomeOnItemClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recentRegistrationsAdapter: RecentRegistrationsAdapter
    private var recentRegistrationsList = ArrayList<RecentRegistrations>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.dashboardTopAppBar.setNavigationOnClickListener { menuItem ->
            when (menuItem.id) {
                R.id.profile -> {
                    requireActivity().run {
                        startActivity(Intent(this, Profile::class.java))
                    }
                    true
                }

                else -> false
            }
        }
        binding.viewMore.setOnClickListener {
            navigateToProgramsListFragment()
        }
        recentRegistrationsList.clear()
        addSampleData()
        underLineText()

        return binding.root
    }

    private fun addSampleData() {
        for (i in 1..4) {
            recentRegistrationsList.add(RecentRegistrations("Grace Wambui", "1/1/23", "2 hours"))
            recentRegistrationsAdapter = RecentRegistrationsAdapter(recentRegistrationsList, this)
            binding.mainDashboardRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            binding.mainDashboardRecyclerview.setHasFixedSize(true)
            binding.mainDashboardRecyclerview.adapter = recentRegistrationsAdapter
        }
    }

    private fun underLineText() {
        val spannableString = SpannableString(binding.viewMore.text)

        spannableString.setSpan(UnderlineSpan(), 0, spannableString.length, 0)

        binding.viewMore.text = spannableString
    }

    override fun onItemClick(item: RecentRegistrations) {
    }
    private fun navigateToProgramsListFragment () {
        val navController = findNavController()
        val action = HomeFragmentDirections.actionHomeFragmentToProgramsListFragment()
        val navOptions = NavOptions.Builder().setPopUpTo(
            R.id.dashboard_home, true
        ).build()
        navController.navigate(action,navOptions )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}