package com.qemer.mwanga.dashboard.programs

import android.app.ProgressDialog
import android.content.Intent
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
import com.qemer.mwanga.dashboard.addchild.AddChildActivity
import com.qemer.mwanga.dashboard.home.RecentRegistrations
import com.qemer.mwanga.dashboard.home.RecentRegistrationsAdapter
import com.qemer.mwanga.databinding.FragmentProgramsListBinding
import com.qemer.mwanga.models.GetChildrenResponse
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

        // Inflate the layout for this fragment
        _binding = FragmentProgramsListBinding.inflate(inflater, container, false)
        apiClient = ApiLoginClient()

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

//    private fun addSampleData() {
//        val progressDialog = ProgressDialog(requireContext())
//        progressDialog.setCancelable(false)
//        progressDialog.setMessage("Fetching...")
//        progressDialog.show()
//
//        apiClient.getApiService(requireContext()).getChildren().enqueue(object :
//            Callback<ArrayList<GetChildrenResponse>> {
//            override fun onResponse(call: Call<ArrayList<GetChildrenResponse>>, response: Response<ArrayList<GetChildrenResponse>>) {
//                Log.d(SuccessModalFragment.TAG, "responseData" + response.body())
//                if (response.isSuccessful) {
//                    progressDialog.dismiss()
//                    programsListAdapter = ProgramsListAdapter(response.body()!!, requireContext())
//                    binding.programsListRecyclerView.adapter = programsListAdapter
//                    val layoutManager: RecyclerView.LayoutManager = object : LinearLayoutManager(requireContext()) {
//                        override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
//                            return RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//                        }
//                    }
//                    binding.programsListRecyclerView.layoutManager = layoutManager
//                    binding.programsListRecyclerView.isNestedScrollingEnabled = true
//                    binding.programsListRecyclerView.setHasFixedSize(true)
//                } else{
//                    progressDialog.dismiss()
//                    Snackbar.make(requireView(), "Failed to get data, Retry!!", Snackbar.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<ArrayList<GetChildrenResponse>>, t: Throwable) {
//                Log.e("Gideon", "onFailure: ${t.message}")
//                Snackbar.make(requireView(), "${t.message}", Snackbar.LENGTH_SHORT).show()
//            }
//        })
//    }

    private fun filterParentInProgramList(text: String) {
        val filteredParentInProgramList = ArrayList<GetGuardiansResponse>()
        for (parent in recentRegistrationsList) {
            val parentInProgramsListName = parent.parentName
            if (parentInProgramsListName.lowercase().contains(text.lowercase(Locale.getDefault()))) {
                filteredParentInProgramList.add(parent)
            }
        }

        if (filteredParentInProgramList.isNotEmpty()) {
            recentRegistrationsAdapter.setFilteredParentList(filteredParentInProgramList)
        }
    }

//    override fun onItemClick(item: GetChildrenResponse) {
//        requireActivity().run {
//            startActivity(Intent(this, AddChildActivity::class.java))
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


