package com.qemer.mwanga.dashboard.home

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.qemer.mwanga.Profile
import com.qemer.mwanga.R
import com.qemer.mwanga.api.ApiLoginClient
import com.qemer.mwanga.dashboard.SuccessModalFragment.Companion.TAG
import com.qemer.mwanga.databinding.FragmentHomeBinding
import com.qemer.mwanga.models.GetGuardiansResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recentRegistrationsAdapter: RecentRegistrationsAdapter
    private var recentRegistrationsList = ArrayList<RecentRegistrations>()
    private lateinit var apiClient: ApiLoginClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        apiClient = ApiLoginClient()

        if (isLocationPermissionGranted()) {
        } else {
            requestLocationPermission()
        }

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

    private fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(requireView(), "Permission granted", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(requireView(), "Permission denied", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
    private fun addSampleData() {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setCancelable(false) // set cancelable to false
        progressDialog.setMessage("Loading...") // set message
        progressDialog.show()

        apiClient.getApiService(requireContext()).getGuardians().enqueue(object :
            Callback<ArrayList<GetGuardiansResponse>> {
            override fun onResponse(call: Call<ArrayList<GetGuardiansResponse>>, response: Response<ArrayList<GetGuardiansResponse>>) {
                Log.d(TAG, "responseData" + response.body())
                if (response.isSuccessful) {
                    progressDialog.dismiss()
                    recentRegistrationsAdapter = RecentRegistrationsAdapter(response.body()!!, requireContext())
                    binding.mainDashboardRecyclerview.adapter = recentRegistrationsAdapter
                    val layoutManager: RecyclerView.LayoutManager = object : LinearLayoutManager(requireContext()) {
                        override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                            return RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        }
                    }
                    binding.mainDashboardRecyclerview.layoutManager = layoutManager
                    binding.mainDashboardRecyclerview.isNestedScrollingEnabled = true
                    binding.mainDashboardRecyclerview.setHasFixedSize(true)
                } else{
                    progressDialog.dismiss()
                    Snackbar.make(requireView(), "Failed to get data, Retry!!", Snackbar.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<GetGuardiansResponse>>, t: Throwable) {
                Log.e("Angela", "onFailure: ${t.message}")
                progressDialog.dismiss()
                Snackbar.make(requireView(), "${t.message}", Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    private fun underLineText() {
        val spannableString = SpannableString(binding.viewMore.text)

        spannableString.setSpan(UnderlineSpan(), 0, spannableString.length, 0)

        binding.viewMore.text = spannableString
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