package com.qemer.mwanga.dashboard.programs

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class ProgramsListFragment : Fragment(), OnItemClickListener {
    private var _binding: FragmentProgramsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var programsListAdapter: ProgramsListAdapter
    private var childrenInProgramList = ArrayList<RecentRegistrations>()
    private lateinit var apiClient: ApiLoginClient

    inner class ProgramsListQueryTextListener : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                filterChildrenInProgramList(query)
            }
            return true
        }

        override fun onQueryTextChange(query: String?): Boolean {
            query?.let {
                filterChildrenInProgramList(query)
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



        childrenInProgramList.clear()
        addSampleData()


        return binding.root
    }

    private fun addSampleData() {

//        for (i in 1..3) {
//            childrenInProgramList.add(RecentRegistrations("Grace Wambui", "1/1/23", "2 hours ago"))
//            childrenInProgramList.add(RecentRegistrations("Bruce Wayne", "1/1/23", "2 hours ago"))
//            childrenInProgramList.add(RecentRegistrations("Damian Wayne", "1/1/23", "2 hours ago"))
//            childrenInProgramList.add(RecentRegistrations("Grace Wambui", "1/1/23", "2 hours ago"))
//            childrenInProgramList.add(RecentRegistrations("Bruce Wayne", "1/1/23", "2 hours ago"))
//            childrenInProgramList.add(RecentRegistrations("Damian Wayne", "1/1/23", "2 hours ago"))
//            childrenInProgramList.add(RecentRegistrations("Angela Adisa", "1/1/23", "2 hours ago"))
//            childrenInProgramList.add(RecentRegistrations("Rediet Hadera", "1/1/23", "2 hours ago"))
//            childrenInProgramList.add(RecentRegistrations("Gloria Lado", "1/1/23", "2 hours ago"))
//            childrenInProgramList.add(RecentRegistrations("Apiu Mary", "1/1/23", "2 hours ago"))
//            childrenInProgramList.add(RecentRegistrations("Joy Mitingi", "1/1/23", "2 hours ago"))
//            childrenInProgramList.add(RecentRegistrations("Gubo Diba", "1/1/23", "2 hours ago"))
//        }

//        programsListAdapter = ProgramsListAdapter(childrenInProgramList, this)
//        binding.programsListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.programsListRecyclerView.setHasFixedSize(true)
//        binding.programsListRecyclerView.adapter = programsListAdapter

        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setCancelable(false) // set cancelable to false
        progressDialog.setMessage("Fetching...") // set message
        progressDialog.show()

        apiClient.getApiService(requireContext()).getChildren().enqueue(object :
            Callback<ArrayList<GetChildrenResponse>> {
            override fun onResponse(call: Call<ArrayList<GetChildrenResponse>>, response: Response<ArrayList<GetChildrenResponse>>) {
                Log.d(SuccessModalFragment.TAG, "responseData" + response.body())
                if (response.isSuccessful) {
                    progressDialog.dismiss()
                    programsListAdapter = ProgramsListAdapter(response.body()!!, requireContext())
                    binding.programsListRecyclerView.adapter = programsListAdapter
                    val layoutManager: RecyclerView.LayoutManager = object : LinearLayoutManager(requireContext()) {
                        override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                            return RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        }
                    }
                    binding.programsListRecyclerView.layoutManager = layoutManager
                    binding.programsListRecyclerView.isNestedScrollingEnabled = true
                    binding.programsListRecyclerView.setHasFixedSize(true)
                } else{
                    progressDialog.dismiss()
                    Snackbar.make(requireView(), "Failed to get data, Retry!!", Snackbar.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<GetChildrenResponse>>, t: Throwable) {
                Log.e("Gideon", "onFailure: ${t.message}")
                Snackbar.make(requireView(), "${t.message}", Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    private fun filterChildrenInProgramList(text: String) {
        val filteredChildrenInProgramList = ArrayList<RecentRegistrations>()
        for (child in childrenInProgramList) {
            val childInProgramsListName = child.name
            if (childInProgramsListName.lowercase().contains(text.lowercase(Locale.getDefault()))) {
                filteredChildrenInProgramList.add(child)
            }
        }

        if (filteredChildrenInProgramList.isNotEmpty()) {
            programsListAdapter.setFilteredChildrenList(filteredChildrenInProgramList)
        }
    }



    override fun onItemClick(item: RecentRegistrations) {
        requireActivity().run {
            startActivity(Intent(this, AddChildActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


