package com.qemer.mwanga.dashboard.programs

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.qemer.mwanga.api.ApiLoginClient
import com.qemer.mwanga.dashboard.SuccessModalFragment
import com.qemer.mwanga.dashboard.home.RecentRegistrationsAdapter
import com.qemer.mwanga.databinding.FragmentProgramsListBinding
import com.qemer.mwanga.models.GetGuardiansResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class ProgramsListFragment : Fragment(){
    private var _binding: FragmentProgramsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recentRegistrationsAdapter: RecentRegistrationsAdapter
    private var recentRegistrationsList = ArrayList<GetGuardiansResponse>()
    private lateinit var originalRecentRegistrationsList: ArrayList<GetGuardiansResponse>
    private lateinit var apiClient: ApiLoginClient

    inner class ProgramsListQueryTextListener : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                filterParentInProgramList(query)
            }
            return true
        }

        override fun onQueryTextChange(query: String?): Boolean {
            query?.let {
                filterParentInProgramList(query)
            }
            return true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProgramsListBinding.inflate(inflater, container, false)
        apiClient = ApiLoginClient()
        originalRecentRegistrationsList = ArrayList()

        binding.dashboardTopAppBar.setNavigationOnClickListener {
            requireActivity().finish()
        }

        val searchView = binding.programsListSearchView
        val queryTextListener = ProgramsListQueryTextListener()

        searchView.setOnQueryTextListener(queryTextListener)

        recentRegistrationsList.clear()
        addSampleData()

        return binding.root
    }

    private fun addSampleData() {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setCancelable(false) // set cancelable to false
        progressDialog.setMessage("Fetching...") // set message
        progressDialog.show()

        apiClient.getApiService(requireContext()).getGuardians().enqueue(object :
            Callback<ArrayList<GetGuardiansResponse>> {
            override fun onResponse(call: Call<ArrayList<GetGuardiansResponse>>, response: Response<ArrayList<GetGuardiansResponse>>) {
                Log.d(SuccessModalFragment.TAG, "responseData" + response.body())
                if (response.isSuccessful) {
                    progressDialog.dismiss()
                    originalRecentRegistrationsList.clear()
                    originalRecentRegistrationsList.addAll(response.body()!!)
                    recentRegistrationsAdapter = RecentRegistrationsAdapter(response.body()!!, requireContext())
                    binding.programsListRecyclerView.adapter = recentRegistrationsAdapter
                    val layoutManager: RecyclerView.LayoutManager = object : LinearLayoutManager(requireContext()) {
                        override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                            return RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        }
                    }
                    binding.programsListRecyclerView.layoutManager = layoutManager
                    binding.programsListRecyclerView.isNestedScrollingEnabled = true
                    binding.programsListRecyclerView.setHasFixedSize(true)
                } else {
                    progressDialog.dismiss()
                    Snackbar.make(requireView(), "Failed to get data, Retry!!", Snackbar.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<GetGuardiansResponse>>, t: Throwable) {
                Log.e("Gideon", "onFailure: ${t.message}")
                progressDialog.dismiss()
                Snackbar.make(requireView(), "${t.message}", Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    private fun filterParentInProgramList(query: String) {
        val filteredList = originalRecentRegistrationsList.filter {
            it.parentName.toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault()))
        } as ArrayList<GetGuardiansResponse>

        recentRegistrationsAdapter.filterList(filteredList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


